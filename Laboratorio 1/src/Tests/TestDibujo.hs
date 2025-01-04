
--import Dibujo ( Dibujo )
import Dibujo (Dibujo, comp, figura, rotar, espejar, rot45, apilar, juntar, encimar,r180, r270,(.-.), (///), (^^^),cuarteto, encimar4, ciclar,foldDib, mapDib,figuras)--, juntar, apilar,figura)
import GHC.Base (undefined)
import Test.HUnit ( (~:), (~?=), runTestTT, Test, Testable(test), Counts, Test(TestCase) )
import Test.HUnit.Base( (~:), (~?=), Counts, Test, Testable(test) )
import Control.Monad (void)


--Nota de los test: creo que no era necesario hacer test de las 
--definiciones de funciones, pero las hice por las dudas
{- Nota2: voy a hacer los test de cada modulo en distos archivos
para que sea mas ordenado, cada test tiene su "main" 
comentado pero se puede usar un main general para cada llamada a función.
NOTA IMPORTANTE: mover este archivo a la carpeta src para probar los test
-}


-- Componer una función n veces
-- Tests: corre bien
testComp :: Test
testComp = test [
    "comp 2 (*2) 1" ~: comp (*2) 2 1 ~?= 4,
    "comp 3 (^2) 2" ~: comp (^2) 3 2 ~?= 256,
    "comp 0 (+1) 0" ~: comp (+1) 0 0 ~?= 0,
    "comp 1 id 'a'" ~: comp id 1 'a' ~?= 'a'
  ]

--main :: IO ()
--main = void $ runTestTT testComp

-- en este test de las definiciones se supone que deberian cumplir la igualdad

--corre bien
testFigura :: Test
testFigura = test [
  "figura entero" ~: figura (3 :: Int) ~?= figura 3,
  "figura char" ~: figura 'a' ~?= figura 'a',
  "figura lista" ~: figura [1,2,3] ~?= figura [1,2,3]
  ]
--La función figura se aplica a varios valores de prueba, (3) y
--se utiliza el operador ~?= para comparar el resultado con el valor esperado.

--main :: IO ()
--main = void $ runTestTT testFigura

testRotar :: Test
testRotar = test [
    "rotar figura" ~: rotar (figura 5) ~?= rotar (figura 5),
    "rotar dibujo" ~: rotar (apilar 1 2 (figura 'a') (figura 'b')) ~?= rotar (apilar 1 2 (figura 'a') (figura 'b')),
    "rotar dibujo complejo" ~: rotar (encimar (rot45 (figura 'a')) (espejar (apilar 3 1 (figura 'b') (figura 'c')))) ~?= rotar (encimar (rot45 (figura 'a')) (espejar (apilar 3 1 (figura 'b') (figura 'c'))))
  ]

--main :: IO ()
--main = void $ runTestTT testRotar


testEspejar :: Test
testEspejar = test [
  "espejar figura" ~: espejar (figura 3) ~?= espejar (figura 3),
  "espejar rotado" ~: espejar (rotar (figura 'a')) ~?= espejar (rotar (figura 'a')),
  "espejar espejado" ~: espejar (espejar (figura "haskell")) ~?= espejar (espejar (figura "haskell"))
  ]

--main :: IO ()
--main = void $ runTestTT testEspejar


testRot45 :: Test
testRot45 = test [
  "espejar figura" ~: rot45 (figura 3) ~?= rot45 (figura 3),
  "espejar rotado" ~: rot45 (rotar (figura 'a')) ~?= rot45 (rotar (figura 'a')),
  "espejar espejado" ~: rot45 (espejar (figura "haskell")) ~?= rot45 (espejar (figura "haskell"))
  ]

--main :: IO ()
--main = void $ runTestTT testRot45


testApilar :: Test
testApilar = test [
    "apilar 1 1 (Figura 'a') (Figura 'b')" ~:
        apilar 1 1 (figura 'a') (figura 'b') ~?= apilar 1.0 1.0 (figura 'a') (figura 'b'),
    "apilar 2 2 (Rotar (Figura 1)) (Figura 2)" ~:
        apilar 2 2 (rotar (figura 1)) (figura 2) ~?= apilar 2.0 2.0 (rotar (figura 1)) (figura 2)
  ]

--main :: IO ()
--main = void $ runTestTT testApilar


testJuntar :: Test
testJuntar = test [
  "juntar" ~: juntar 2 1 (figura 'a') (figura 'b') ~?= juntar 2 1 (figura 'a') (figura 'b'),
  "juntar 2" ~: juntar 0.5 0.2 (figura 3) (rotar (figura 4)) ~?= juntar 0.5 0.2 (figura 3) (rotar (figura 4)),
  "juntar 3" ~: juntar 1 1.5 (figura "haskell") (rot45 (figura "hola")) ~?= juntar 1 1.5 (figura "haskell") (rot45 (figura "hola"))
  ]

--main :: IO ()
--main = void $ runTestTT testJuntar


testEncimar :: Test
testEncimar = test [
  "encimar figuras" ~: encimar (figura 'a') (figura 'b') ~?= encimar (figura 'a') (figura 'b'),
  "encimar rotados" ~: encimar (rotar $ figura 1) (rotar $ figura 2) ~?= encimar (rotar $ figura 1) (rotar $ figura 2),
  "encimar apilados" ~: encimar (apilar 1 1 (figura 1) (figura 2)) (apilar 2 2 (figura 3) (figura 4)) ~?= encimar (apilar 1 1 (figura 1) (figura 2)) (apilar 2 2 (figura 3) (figura 4))
  ]

--main :: IO ()
--main = void $ runTestTT testJuntar

-- Rotaciones de múltiplos de 90.

testR180 :: Test
testR180 = test [
    "r180 de figura" ~: r180 (figura 5) ~?= rotar(rotar(figura 5)),
    "r180 de dibujo compuesto" ~: r180 (apilar 1 1 (figura 'a') (figura 'b')) ~?= rotar(rotar(apilar 1 1 (figura 'a') (figura 'b')))
   -- "r180 de dibujo con rotaciones y espejos" ~: r180 (juntar 1 1 (espejar (rotar (figura 'a'))) (rot45 (figura 'b'))) ~?= juntar 1 1 (rotar (rotar (rotar (espejar (figura 'a'))))) (espejar (rotar (rotar (rotar (rot45 (figura 'b'))))))
  ]

--main :: IO ()
--main = void $ runTestTT testR180

testR270 :: Test
testR270 = test [
    "r270 de figura" ~: r270 (figura 'a') ~?= rotar (rotar (rotar (figura 'a'))),
    "r270 de dibujo compuesto" ~: r270 (apilar 1 1 (figura 'a') (figura 'b')) ~?= rotar (rotar (rotar (apilar 1 1 (figura 'a') (figura 'b') )))
  ]

--main :: IO ()
--main = void $ runTestTT testR180

-- Pone una figura sobre la otra, ambas ocupan el mismo espacio.

testApil :: Test
testApil = test [ 
    "Figura sobre figura" ~:(figura "a" .-. figura "b") ~?= apilar 1 1 (figura "a") (figura "b"),
    "Figura sobre figura" ~: (rotar(figura "a") .-. rotar(figura "b")) ~?= apilar 1 1 (rotar(figura "a")) (rotar(figura "b"))
  ]

--main :: IO Counts
--main = runTestTT testApil

-- Pone una figura al lado de la otra, ambas ocupan el mismo espacio.

testJunt :: Test
testJunt = test
  [ "Juntar 2 figuras iguales ocupa el mismo espacio" ~: (figura 'A' /// figura 'A') ~?= juntar 1.0 1.0 (figura 'A') (figura 'A'),
    "Juntar 2 figuras distintas ocupa el mismo espacio" ~: (figura 'A' /// figura 'B') ~?= juntar 1.0 1.0 (figura 'A') (figura 'B'),
    "Figura rotada junto a figura rotada" ~: (rotar(figura "a") /// rotar(figura "b")) ~?= juntar 1 1 (rotar(figura "a")) (rotar(figura "b"))
    ]
--main :: IO Counts
--main = runTestTT testJunt

-- Superpone una figura con otra.

testEncim :: Test
testEncim = test
  [ "Juntar 2 figuras iguales ocupa el mismo espacio" ~: (figura 'A' ^^^ figura 'A') ~?= encimar (figura 'A') (figura 'A'),
    "Juntar 2 figuras distintas ocupa el mismo espacio" ~: (figura 'A' ^^^ figura 'B') ~?= encimar (figura 'A') (figura 'B'),
    "Figura rotada junto a figura rotada" ~: (rotar(figura "a") ^^^ rotar(figura "b")) ~?= encimar (rotar(figura "a")) (rotar(figura "b"))
    ]
--main :: IO Counts
--main = runTestTT testEncim
-- Lo que tomo por superponer es poner dos figuras en el mismo lugar

-- Dadas cuatro figuras las ubica en los cuatro cuadrantes.
-- voy a tomar cuadrante como el lugar que ocupa una figura.

testCuarteto :: Test
testCuarteto = test [
    "Cuatro figuras iguales" ~: cuarteto f f f f ~?= ((f /// f) .-. (f /// f)),
    "Cuatro figuras distintas" ~: cuarteto a b c d ~?= ((a /// b) .-. (c /// d))
  ]
  where
    f = figura "figura"
    a = figura "figura A"
    b = figura "figura B"
    c = figura "figura C"
    d = figura "figura D"

-- Ejecución de los tests
--main :: IO Counts
--main = runTestTT testCuarteto

-- Una figura repetida con las cuatro rotaciones, superpuestas.

testEncimar4 :: Test
testEncimar4 = test [
    encimar4 (figura 1) ~?= encimar (encimar (figura 1) (rotar (figura 1))) (encimar (rotar( rotar(figura 1))) (rotar (rotar (rotar (figura 1))))),
    encimar4 (figura 'a') ~?= encimar (encimar (figura 'a') (rotar (figura 'a'))) (encimar (rotar( rotar(figura 'a'))) (rotar (rotar (rotar (figura 'a')))))
  ]
--main :: IO Counts
--main = runTestTT testEncimar4
-- Cuadrado con la misma figura rotada i * 90, para i ∈ {0, ..., 3}.

testCiclar :: Test
testCiclar = test [
    ciclar (figura 1) ~?= cuarteto (figura 1) (rotar (figura 1)) (rotar (rotar (figura 1))) (rotar (rotar (rotar (figura 1)))),
    ciclar (figura 'a') ~?= cuarteto (figura 'a') (rotar (figura 'a')) (rotar (rotar (figura 'a'))) (rotar (rotar (rotar (figura 'a')))),
    ciclar (figura "a") ~?= cuarteto (figura "a") (rotar (figura "a")) (rotar (rotar (figura "a"))) (rotar (rotar (rotar (figura "a"))))
  ]
--main :: IO Counts
--main = runTestTT testCiclar

-- Estructura general para la semántica.
{- foldDib :: (a -> b) -> (b -> b) -> (b -> b) -> (b -> b) ->
       (Float -> Float -> b -> b -> b) -> 
       (Float -> Float -> b -> b -> b) -> 
       (b -> b -> b) ->
       Dibujo a -> b
foldDib f g h i j k l (Figura a) = f a --f g h i j k l a
foldDib f g h i j k l (Rotar a) = g (foldDib f g h i j k l a) 
foldDib f g h i j k l (Espejar a) = h (foldDib f g h i j k l a)
foldDib f g h i j k l (Rot45 a) = i (foldDib f g h i j k l a) 
foldDib f g h i j k l (Apilar x y a b) = j x y (foldDib f g h i j k l a) (foldDib f g h i j k l b)
foldDib f g h i j k l (Juntar x y a b) = k x y (foldDib f g h i j k l a) (foldDib f g h i j k l b)
foldDib f g h i j k l (Encimar a b) = l (foldDib f g h i j k l a) (foldDib f g h i j k l b)

 -}

testFoldDib :: Test
testFoldDib = test[
  "Figura" ~: foldDib figura id id id apilar juntar encimar (figura (1 :: Int)) ~?= figura (1 :: Int),
  "Rotar" ~: foldDib figura id id id apilar juntar encimar (rotar (figura (1 :: Int))) ~?= figura (1 :: Int),
  "Espejar" ~: foldDib figura id id id apilar juntar encimar (espejar (figura (1 :: Int))) ~?= figura (1 :: Int),
  "Rot45" ~: foldDib figura id id id apilar juntar encimar (rot45 (figura (1 :: Int))) ~?= figura (1 :: Int),
  "Apilar" ~: foldDib figura id id id apilar juntar encimar (apilar 1 1 (figura (1 :: Int)) (figura (1 :: Int))) ~?= apilar 1 1 (figura (1 :: Int)) (figura (1 :: Int)),
  "Juntar" ~: foldDib figura id id id apilar juntar encimar (juntar 1 1 (figura (1 :: Int)) (figura (1 :: Int))) ~?= juntar 1 1 (figura (1 :: Int)) (figura (1 :: Int)),
  "Encimar" ~: foldDib figura id id id apilar juntar encimar (encimar (figura (1 :: Int)) (figura (1 :: Int))) ~?= encimar (figura (1 :: Int)) (figura (1 :: Int))
  ]   

--main :: IO Counts
--main = runTestTT testFoldDib

-- Demostrar que `mapDib figura = id`

testMapDib :: Test
testMapDib = test [
    "Figura" ~: mapDib figura (figura (1 :: Int)) ~?= figura (1 :: Int),
    "Rotar" ~: mapDib figura (rotar (figura (1 :: Int))) ~?= rotar (figura (1 :: Int)),
    "Espejar" ~: mapDib figura (espejar (figura (1 :: Int))) ~?= espejar (figura (1 :: Int)),
    "Rot45" ~: mapDib figura (rot45 (figura (1 :: Int))) ~?= rot45 (figura (1 :: Int)),
    "Apilar" ~: mapDib figura (apilar 1 1 (figura (1 :: Int)) (figura (1 :: Int))) ~?= apilar 1 1 (figura (1 :: Int)) (figura (1 :: Int)),
    "Juntar" ~: mapDib figura (juntar 1 1 (figura (1 :: Int)) (figura (1 :: Int))) ~?= juntar 1 1 (figura (1 :: Int)) (figura (1 :: Int)),
    "Encimar" ~: mapDib figura (encimar (figura (1 :: Int)) (figura (1 :: Int))) ~?= encimar (figura (1 :: Int)) (figura (1 :: Int))
  ]
--main :: IO Counts
--main = runTestTT testMapDib

-- Junta todas las figuras básicas de un dibujo.

testFiguras :: Test
testFiguras = test [
    "Figura" ~: figuras (figura (1 :: Int)) ~?= [1],
    "Rotar" ~: figuras (rotar (figura (1 :: Int))) ~?= [1],
    "Espejar" ~: figuras (espejar (figura (1 :: Int))) ~?= [1],
    "Rot45" ~: figuras (rot45 (figura (1 :: Int))) ~?= [1],
    "Apilar" ~: figuras (apilar 1 1 (figura (1 :: Int)) (figura (1 :: Int))) ~?= [1,1],
    "Juntar" ~: figuras (juntar 1 1 (figura (1 :: Int)) (figura (1 :: Int))) ~?= [1,1],
    "Encimar" ~: figuras (encimar (figura (1 :: Int)) (figura (1 :: Int))) ~?= [1,1]
  ]
main :: IO Counts
main = runTestTT testFiguras