module Dibujos.Alfombra (
    interpAlfombra,
    confAlfombra
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

type Sier = Bool

juntar3 :: Dibujo Sier -> Dibujo Sier -> Dibujo Sier -> Dibujo Sier
juntar3 a b c = juntar 1 2 (juntar 1 1 a b) c

alfombra :: Int -> Dibujo Sier -> Dibujo Sier
alfombra n d | n == 1 = figura True
               | otherwise = apilar 1 2 (apilar 1 1 (juntar3 (alfombra (n-1) d) (alfombra (n-1) d) (alfombra (n-1) d)) (juntar3 (alfombra (n-1) d) (figura False) (alfombra (n-1) d))) (juntar3 (alfombra (n-1) d) (alfombra (n-1) d) (alfombra (n-1) d))

interpAlfombra :: Output Sier
interpAlfombra False _ _ _ = Blank
interpAlfombra True x y w = polygon $ rectangulo x y w
  where
      rectangulo x y w = [x, x V.+ y, x V.+ y V.+ w, x V.+ w, x]

confAlfombra :: Conf
confAlfombra = Conf {
    name = "Alfombra",
    pic = interp interpAlfombra (alfombra 6 (figura True))
}