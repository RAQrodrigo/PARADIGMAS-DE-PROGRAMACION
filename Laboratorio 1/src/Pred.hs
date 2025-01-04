module Pred (
  Pred,
  cambiar, anyDib, allDib, orP, andP, cambiarBas --agregue cambiarBas
) where

import Dibujo
import GHC.Base (undefined)
--import Text.XHtml (base)

type Pred a = a -> Bool

cambiarBas :: Pred a -> (a -> Dibujo a) -> a -> Dibujo a 
cambiarBas pr g d | pr d = g d
                 | otherwise = figura d

-- Dado un predicado sobre básicas, cambiar todas las que satisfacen
-- el predicado por la figura básica indicada por el segundo argumento.
cambiar :: Pred a -> (a -> Dibujo a) -> Dibujo a -> Dibujo a
cambiar f g = mapDib (cambiarBas f g)


-- Alguna básica satisface el predicado.
--Deberia ingresar un predicado y ver si alguna basica satisface el mismo.
anyDib :: Pred a -> Dibujo a -> Bool
anyDib f = foldDib f id id id (\ _ _ a b -> a || b) (\ _ _ a b -> a || b) (||) (\ _ _ a -> id a)

-- digamos que f es un funcion que me devuelve true cuando se encuentra con un cuadrado, tonces esta
-- esta funciona deberia volverme true si es que existe algun cuadrado en un conjunto de dibujos

-- Todas las básicas satisfacen el predicado.
--En el contexto de la función allDib, "todas las básicas" 
--se refiere a los elementos más simples de un dibujo, como pueden ser una figura, 
--una rotación, un espejo, etc.
--La expresión "Todas las básicas satisfacen el predicado" significa que si 
--aplicamos una función f de tipo Pred a (que toma un valor de tipo a y devuelve 
--un valor de tipo Bool) a cualquier dibujo básico (es decir, una figura, una rotación, un espejo, etc.), 
--entonces el resultado debe ser True.
--En otras palabras, se espera que la función allDib verifique que cada uno de los elementos más básicos del 
--dibujo cumpla con el predicado f.

allDib :: Pred a -> Dibujo a -> Bool
allDib f = foldDib f id id id (\ _ _ a b -> a && b) (\ _ _ a b -> a && b) (&&) (\ _ _ a -> id a)

-- Los dos predicados se cumplen para el elemento recibido.
andP :: Pred a -> Pred a -> Pred a
andP f g a = f a && g a

-- Algún predicado se cumple para el elemento recibido.
orP :: Pred a -> Pred a -> Pred a
orP f g a = f a || g a
