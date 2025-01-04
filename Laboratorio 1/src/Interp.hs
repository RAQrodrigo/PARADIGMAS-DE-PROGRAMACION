module Interp (
    interp,
    Conf(..),
    interpConf,
    initial
) where

import Graphics.Gloss(Vector, Picture, Display(InWindow), makeColorI, color, pictures, translate, white, display)
import Dibujo (Dibujo, foldDib)
import FloatingPic (FloatingPic, Output, half, grid)
import qualified Graphics.Gloss.Data.Point.Arithmetic as V
import qualified Graphics.Gloss.Data.Vector as V


--Funciones auxiliares

rotar :: (FloatingPic -> FloatingPic)
rotar a x w h = a (x V.+ w) h (V.negate w)

espejar ::(FloatingPic -> FloatingPic)
espejar a x w h = a (x V.+w) (V.negate w) h 

rot45 :: (FloatingPic -> FloatingPic)
rot45 a x w h = a (x V.+ half w) m j
            where --Warning amicha: ¡Notar que se va del espacio asignado por las coordenadas iniciales!
                m = (0.5) V.* (w V.+ h)
                j = (0.5) V.* (h V.- w)

apilar :: (Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic)
apilar n m a b x w h = pictures [(a (x V.+ h') w (r V.* h)), b x w h']
                                where 
                                    r = m/(m+n)
                                    r' = n/(m+n) 
                                    h' = V.mulSV r' h

juntar :: (Float -> Float -> FloatingPic -> FloatingPic -> FloatingPic)
juntar n m a b x w h = pictures[(a x w' h), (b (x V.+w') (r' V.* w) h)]
                                where
                                    r = m/(m+n) --no es un vector sino un float
                                    w' = r V.* w --retorna un vector
                                    r' = n/(m+n)

encimar :: (FloatingPic -> FloatingPic -> FloatingPic)
encimar a b x h w = pictures [(a x h w), (b x h w)] 

propor :: (Float -> Float -> FloatingPic -> FloatingPic)
propor n m a x w h | n > m = a x h ((m/n) V.* w)
                   | otherwise = a x ((n/m) V.* h) w

--Interpretación del dibujo
interp :: Output a -> Output (Dibujo a)
interp f  = foldDib f rotar espejar rot45 apilar juntar encimar propor

-- Configuración de la interpretación
data Conf = Conf {
        name :: String,
        pic :: FloatingPic
    }

interpConf :: Conf -> Float -> Float -> Picture 
interpConf (Conf _ p) x y = p (0, 0) (x,0) (0,y)

-- Dada una computación que construye una configuración, mostramos por
-- pantalla la figura de la misma de acuerdo a la interpretación para
-- las figuras básicas. Permitimos una computación para poder leer
-- archivos, tomar argumentos, etc.
initial :: Conf -> Float -> IO ()
initial cfg size = do
    let n = name cfg
        win = InWindow n (ceiling size, ceiling size) (0, 0)
    display win white $ withGrid (interpConf cfg size size) size size
  where withGrid p x y = translate (-size/2) (-size/2) $ pictures [p, color grey $ grid (ceiling $ size / 10) (0, 0) x 10]
        grey = makeColorI 120 120 120 120