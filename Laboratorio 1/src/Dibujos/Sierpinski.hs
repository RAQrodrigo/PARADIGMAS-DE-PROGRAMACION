module Dibujos.Sierpinski (
    interpSierpinski,
    confSierpinski
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

triangle :: Dibujo Sier
triangle = juntar 1 1 (espejar(figura True)) (figura True)

sierpinski :: Int -> Dibujo Sier -> Dibujo Sier
sierpinski n d | n == 1 = triangle
               | otherwise = apilar 1 1 (juntar 3 1 (figura False) (juntar 1 2 (sierpinski (n-1) d) (figura False))) (juntar 1 1 (sierpinski (n-1) d) (sierpinski (n-1) d))

interpSierpinski :: Output Sier
interpSierpinski False _ _ _ = Blank
interpSierpinski True a b c = polygon $ triangulo a b c
  where
      triangulo a b c = map (a V.+) [zero, c, b, zero]

confSierpinski :: Conf
confSierpinski = Conf {
    name = "Sierpinski",
    pic = interp interpSierpinski (sierpinski 5 triangle)
}