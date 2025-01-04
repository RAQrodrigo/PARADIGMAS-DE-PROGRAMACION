module Dibujos.Escher (
    interpEscher,
    confEscher
) where

import Graphics.Gloss (white, line, polygon, pictures)

import qualified Graphics.Gloss.Data.Point.Arithmetic as V

import FloatingPic (Output, half, zero)
import Interp (Conf(..), interp)

import Dibujo (Dibujo, figura, rotar, espejar, rot45, apilar, juntar, encimar, r180, r270, cuarteto, ciclar)
import Graphics.Gloss
import Graphics.Gloss.Data.Picture
import Graphics.Gloss.Interface.Pure.Game
import Graphics.Gloss.Data.Color
import qualified Graphics.Gloss.Data.Vector as V

type Escher = Bool

-- El dibujoU.
dibujo_u :: Dibujo Escher -> Dibujo Escher
dibujo_u p = encimar (espejar(encimar (rot45 p) (rotar (rot45 p)))) (r180 (espejar(encimar (rot45 p) (rotar (rot45 p)))))

-- El dibujo t.
dibujo_t :: Dibujo Escher -> Dibujo Escher
dibujo_t p = encimar p (espejar(encimar (rot45 p) (rotar (rot45 p))))


-- Esquina con nivel de detalle en base a la figura p.
esquina :: Int -> Dibujo Escher -> Dibujo Escher
esquina n p | n == 1 = cuarteto (figura False) (figura False) (figura False) (dibujo_u p)
            | otherwise = cuarteto (esquina (n-1) p) (lado (n-1) p) (rotar (lado (n-1) p)) (dibujo_u p) 


-- Lado con nivel de detalle.
lado :: Int -> Dibujo Escher -> Dibujo Escher
lado n p | n == 1 = cuarteto (figura False) (figura False) (rotar (dibujo_t p)) (dibujo_t p)
         | otherwise = cuarteto (lado (n-1) p) (lado (n-1) p) (rotar (dibujo_t p)) (dibujo_t p)


noneto p q r s t u v w x = 
  apilar 2 1 (juntar 2 1 p (juntar 1 1 q r)) (apilar 1 1 (juntar 2 1 s (juntar 1 1 t u)) (juntar 2 1 v (juntar 1 1 w x)))


-- El dibujo de Escher:
escher :: Int -> Escher -> Dibujo Escher
escher n f = noneto p q r s t u v w x 
    where 
        p = esquina n (figura f)
        q = lado n (figura f)
        r = r270 (esquina n (figura f))
        s = rotar (lado n (figura f))
        t = dibujo_u (figura f)
        u = r270 (lado n (figura f))
        v = rotar(esquina n (figura f))
        w = r180 (lado n (figura f))
        x = r180 (esquina n (figura f))


interpEscher :: Output Escher
interpEscher False x y w = Blank
interpEscher True x y w = line $ map (x V.+) [zero, w, y, zero]

confEscher :: Conf
confEscher = Conf {
    name = "Escher",
    pic = interp interpEscher (escher 2 True)
}