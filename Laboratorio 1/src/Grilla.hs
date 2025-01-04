module Grilla (
    grilla,
    interptext,
    confGrilla
) where

import Graphics.Gloss (Picture, blue, color, line, pictures, red, white)

import qualified Graphics.Gloss.Data.Point.Arithmetic as V

import Dibujo (Dibujo, figura, juntar, apilar, rot45, rotar, encimar, espejar)
import FloatingPic (Output, half, zero)
import Interp (Conf(..), interp)

import Graphics.Gloss
import Graphics.Gloss.Data.Picture
import Graphics.Gloss.Interface.Pure.Game
import Graphics.Gloss.Data.Color

interptext :: Output Picture
interptext t (a,b) y z = translate (a+10) (b+10) (t) 

figtext :: Picture -> Dibujo Picture
figtext t = figura (t)

row :: [Dibujo a] -> Dibujo a
row [] = error "row: no puede ser vacío"
row [d] = d
row (d:ds) = juntar (fromIntegral $ length ds) 1 d (row ds)

column :: [Dibujo a] -> Dibujo a
column [] = error "column: no puede ser vacío"
column [d] = d
column (d:ds) = apilar (fromIntegral $ length ds) 1 d (column ds)

grilla :: [[Dibujo a]] -> Dibujo a
grilla = column . map row

coordenadas :: Dibujo Picture
coordenadas = grilla [
    [figtext (scale 0.1 0.1 (text "(0,0)")),figtext (scale 0.1 0.1 (text "(0,1)")),figtext (scale 0.1 0.1 (text "(0,2)")),figtext (scale 0.1 0.1 (text "(0,3)")),figtext (scale 0.1 0.1 (text "(0,4)")),figtext (scale 0.1 0.1 (text "(0,5)")),figtext (scale 0.1 0.1 (text "(0,6)")),figtext (scale 0.1 0.1 (text "(0,7)"))] ,
    [figtext (scale 0.1 0.1 (text "(1,0)")),figtext (scale 0.1 0.1 (text "(1,1)")),figtext (scale 0.1 0.1 (text "(1,2)")),figtext (scale 0.1 0.1 (text "(1,3)")),figtext (scale 0.1 0.1 (text "(1,4)")),figtext (scale 0.1 0.1 (text "(1,5)")),figtext (scale 0.1 0.1 (text "(1,6)")),figtext (scale 0.1 0.1 (text "(1,7)"))] ,
    [figtext (scale 0.1 0.1 (text "(2,0)")),figtext (scale 0.1 0.1 (text "(2,1)")),figtext (scale 0.1 0.1 (text "(2,2)")),figtext (scale 0.1 0.1 (text "(2,3)")),figtext (scale 0.1 0.1 (text "(2,4)")),figtext (scale 0.1 0.1 (text "(2,5)")),figtext (scale 0.1 0.1 (text "(2,6)")),figtext (scale 0.1 0.1 (text "(2,7)"))] ,
    [figtext (scale 0.1 0.1 (text "(3,0)")),figtext (scale 0.1 0.1 (text "(3,1)")),figtext (scale 0.1 0.1 (text "(3,2)")),figtext (scale 0.1 0.1 (text "(3,3)")),figtext (scale 0.1 0.1 (text "(3,4)")),figtext (scale 0.1 0.1 (text "(3,5)")),figtext (scale 0.1 0.1 (text "(3,6)")),figtext (scale 0.1 0.1 (text "(3,7)"))] ,
    [figtext (scale 0.1 0.1 (text "(4,0)")),figtext (scale 0.1 0.1 (text "(4,1)")),figtext (scale 0.1 0.1 (text "(4,2)")),figtext (scale 0.1 0.1 (text "(4,3)")),figtext (scale 0.1 0.1 (text "(4,4)")),figtext (scale 0.1 0.1 (text "(4,5)")),figtext (scale 0.1 0.1 (text "(4,6)")),figtext (scale 0.1 0.1 (text "(4,7)"))] ,
    [figtext (scale 0.1 0.1 (text "(5,0)")),figtext (scale 0.1 0.1 (text "(5,1)")),figtext (scale 0.1 0.1 (text "(5,2)")),figtext (scale 0.1 0.1 (text "(5,3)")),figtext (scale 0.1 0.1 (text "(5,4)")),figtext (scale 0.1 0.1 (text "(5,5)")),figtext (scale 0.1 0.1 (text "(5,6)")),figtext (scale 0.1 0.1 (text "(5,7)"))] ,
    [figtext (scale 0.1 0.1 (text "(6,0)")),figtext (scale 0.1 0.1 (text "(6,1)")),figtext (scale 0.1 0.1 (text "(6,2)")),figtext (scale 0.1 0.1 (text "(6,3)")),figtext (scale 0.1 0.1 (text "(6,4)")),figtext (scale 0.1 0.1 (text "(6,5)")),figtext (scale 0.1 0.1 (text "(6,6)")),figtext (scale 0.1 0.1 (text "(6,7)"))] ,
    [figtext (scale 0.1 0.1 (text "(7,0)")),figtext (scale 0.1 0.1 (text "(7,1)")),figtext (scale 0.1 0.1 (text "(7,2)")),figtext (scale 0.1 0.1 (text "(7,3)")),figtext (scale 0.1 0.1 (text "(7,4)")),figtext (scale 0.1 0.1 (text "(7,5)")),figtext (scale 0.1 0.1 (text "(7,6)")),figtext (scale 0.1 0.1 (text "(7,7)"))] 
    ]

confGrilla:: Conf
confGrilla = Conf {
    name = "Grilla",
    pic = interp interptext coordenadas
}

