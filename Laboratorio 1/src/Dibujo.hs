{-# LANGUAGE LambdaCase #-}
module Dibujo (
    Dibujo, comp,
    figura, rotar, espejar, rot45, apilar, juntar, encimar, proporciones,
    r180, r270,
    (.-.), (///), (^^^),
    cuarteto, encimar4, ciclar,
    foldDib, mapDib,
    figuras
) where
import GHC.Parser.Lexer (xset)
import GHC.Plugins (HoleFitCandidate(IdHFCand))

data Dibujo a = Figura a | Rotar (Dibujo a) | Espejar (Dibujo a) | Rot45 (Dibujo a)
    | Apilar Float Float (Dibujo a) (Dibujo a) 
    | Juntar Float Float (Dibujo a) (Dibujo a) 
    | Encimar (Dibujo a) (Dibujo a)
    | Proporciones Float Float (Dibujo a)
    deriving (Eq, Show)


-- Construcción de dibujo. Abstraen los constructores. fijarse si esta bien
figura :: a -> Dibujo a
figura = Figura

rotar :: Dibujo a -> Dibujo a
rotar = Rotar

espejar :: Dibujo a -> Dibujo a
espejar = Espejar

rot45 :: Dibujo a -> Dibujo a
rot45 = Rot45

apilar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
apilar = Apilar

juntar :: Float -> Float -> Dibujo a -> Dibujo a -> Dibujo a
juntar = Juntar

encimar :: Dibujo a -> Dibujo a ->Dibujo a
encimar = Encimar

proporciones :: Float -> Float -> Dibujo a -> Dibujo a
proporciones = Proporciones

-- Componer una función n veces
comp :: (a -> a) -> Int -> a -> a
comp f n | n <= 0 = id 
          | otherwise = f . comp f (n-1)

-- Rotaciones de múltiplos de 90.
r180 :: Dibujo a -> Dibujo a
r180 = comp Rotar 2

r270 :: Dibujo a -> Dibujo a
r270 = r180 . rotar

-- Pone una figura sobre la otra, ambas ocupan el mismo espacio.
(.-.) :: Dibujo a -> Dibujo a -> Dibujo a
(.-.) = apilar 1 1 

-- Pone una figura al lado de la otra, ambas ocupan el mismo espacio.
(///) :: Dibujo a -> Dibujo a -> Dibujo a
(///) = Juntar 1.0 1.0 

-- Superpone una figura con otra.
(^^^) :: Dibujo a -> Dibujo a -> Dibujo a
(^^^) = encimar
-- Lo que tomo por superponer es poner dos figuras en el mismo lugar

-- Dadas cuatro figuras las ubica en los cuatro cuadrantes.
-- voy a tomar cuadrante como el lugar que ocupa una figura.
cuarteto :: Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a -> Dibujo a
cuarteto a b c d = (.-.) ((///) a b) ((///) c d) 

-- Una figura repetida con las cuatro rotaciones, superpuestas.
encimar4 :: Dibujo a -> Dibujo a
encimar4 a = encimar (encimar a (rotar a)) (encimar (comp rotar 2 a) (comp rotar 3 a))

-- Cuadrado con la misma figura rotada i * 90, para i ∈ {0, ..., 3}.
ciclar :: Dibujo a -> Dibujo a
ciclar a = cuarteto a (comp rotar 3 a) (comp rotar 1 a) (comp rotar 2 a)

-- Estructura general para la semántica.
foldDib :: (a -> b) -> (b -> b) -> (b -> b) -> (b -> b) ->
       (Float -> Float -> b -> b -> b) -> 
       (Float -> Float -> b -> b -> b) -> 
       (b -> b -> b) ->
       (Float -> Float -> b -> b) -> 
       Dibujo a -> b
foldDib f g h i j k l m (Figura a) = f a --f g h i j k l m a
foldDib f g h i j k l m (Rotar a) = g (foldDib f g h i j k l m a) 
foldDib f g h i j k l m (Espejar a) = h (foldDib f g h i j k l m a)
foldDib f g h i j k l m (Rot45 a) = i (foldDib f g h i j k l m a) 
foldDib f g h i j k l m (Apilar x y a b) = j x y (foldDib f g h i j k l m a) (foldDib f g h i j k l m b)
foldDib f g h i j k l m (Juntar x y a b) = k x y (foldDib f g h i j k l m a) (foldDib f g h i j k l m b)
foldDib f g h i j k l m (Encimar a b) = l (foldDib f g h i j k l m a) (foldDib f g h i j k l m b)
foldDib f g h i j k l m (Proporciones x y a) = m x y (foldDib f g h i j k l m a)

-- Demostrar que `mapDib figura = id`
mapDib :: (a -> Dibujo b) -> Dibujo a -> Dibujo b
mapDib f (Figura a) = f a
mapDib f (Rotar a) = Rotar (mapDib f a)
mapDib f (Espejar a) = Espejar (mapDib f a)
mapDib f (Rot45 a) = Rot45 (mapDib f a)
mapDib f (Apilar x y a b) = Apilar x y (mapDib f a) (mapDib f b)
mapDib f (Juntar x y a b) = Juntar x y (mapDib f a) (mapDib f b)
mapDib f (Encimar a b) = Encimar (mapDib f a) (mapDib f b)

-- Junta todas las figuras básicas de un dibujo.
figuras :: Dibujo a -> [a]
figuras (Figura a) = [a]
figuras (Rotar a) = figuras a
figuras (Espejar a) = figuras a
figuras (Rot45 a) = figuras a
figuras (Apilar _ _ a b) = figuras a ++ figuras b
figuras (Juntar _ _ a b) = figuras a ++ figuras b
figuras (Encimar a b) = figuras a ++ figuras b
