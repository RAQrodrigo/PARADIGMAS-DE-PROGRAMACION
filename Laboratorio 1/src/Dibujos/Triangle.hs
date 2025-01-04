module Dibujos.Triangle (
    interpTriangle,
    confTriangle
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

type Triangle = Int

basCuadrante :: Dibujo Triangle
basCuadrante = encimar (figura 1) (r180 (figura 2))

trin :: Dibujo Triangle
trin = cuarteto basCuadrante (espejar basCuadrante) (r270 basCuadrante) (figura 0)

triangless :: Int -> Dibujo Triangle -> Dibujo Triangle
triangless 0 d = figura 0
triangless n d = cuarteto basCuadrante (espejar basCuadrante) (r270 basCuadrante) (triangless (n-1) d)

fractal :: Dibujo Triangle
fractal = ciclar (triangless 9 (figura 0))

interpTriangle :: Output Triangle
interpTriangle 0 _ _ _ = Blank
interpTriangle 1 x y w = color orange $ polygon $ map (x V.+) [zero, w, y, zero]
interpTriangle 2 x y w = color violet $ polygon $ map (x V.+) [zero, w, y, zero]

confTriangle :: Conf
confTriangle = Conf {
    name = "Triangle",
    pic = interp interpTriangle fractal
}