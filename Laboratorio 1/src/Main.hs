module Main (main) where

import Data.Maybe (fromMaybe)
import System.Console.GetOpt (ArgDescr(..), ArgOrder(..), OptDescr(..), getOpt)
import System.Environment (getArgs)
import Text.Read (readMaybe)

import Interp (Conf(name), initial)
import Dibujos.Escher (confEscher)
import Dibujos.Feo (feoConf)
import Dibujos.Ejemplo (ejemploConf)
import Grilla (confGrilla)
import Dibujos.Sierpinski (confSierpinski)
import Dibujos.Alfombra (confAlfombra)
import Dibujos.Triangle  (confTriangle)

-- Lista de configuraciones de los dibujos
configs :: [Conf]
configs = [confEscher, feoConf, ejemploConf, confGrilla, confSierpinski, confAlfombra, confTriangle]

string' :: [Conf] -> String
string' (c : []) = name c
string' (c : cs) = (name c) ++ "\n" ++ (string' cs)

-- Imprime los dibujos disponibles
print' :: [Conf] -> IO ()
print' c = putStrLn $ (string' c)

-- Dibuja el dibujo n
initial' :: [Conf] -> String -> IO ()
initial' c n | n == "--list" = print' c
initial' [] n = do
    putStrLn $ "No hay un dibujo llamado " ++ n
initial' (c : cs) n = 
    if n == name c then
        initial c 400
    else
        initial' cs n

-- Pregunta al usuario después de ejecutar --list
mainLoop :: [Conf] -> IO ()
mainLoop c = do
    putStrLn "Ingrese un dibujo para mostrar (o 'quit' para salir):"
    input <- getLine
    if input == "quit"
        then return ()
        else initial' c input >> mainLoop c

main :: IO ()
main = do
    args <- getArgs
    initial' configs $ head args
    mainLoop configs
