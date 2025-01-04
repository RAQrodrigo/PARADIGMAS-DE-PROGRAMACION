
import Pred (Pred, cambiar, anyDib, allDib, orP, andP, cambiarBas)
import Dibujo ( Dibujo, figura, foldDib, mapDib, juntar, apilar, rotar, espejar, rot45, encimar, r180, r270, (.-.), (///), (^^^), cuarteto, encimar4, ciclar )
import GHC.Base (undefined)
import Control.Monad (void)
import Test.HUnit ( (~:), (~?=), runTestTT, Test, Testable(test), Counts, Test(TestCase))
import Test.HUnit.Base( (~:), (~?=), Counts, Test, Testable(test))


{- NOTA IMPORTANTE:mover este archivo a la carpeta src para probar los test -}


testCambiarBas :: Test
testCambiarBas = "Test de cambiarBas" ~: test [
  cambiarBas (==3) (figura . (*2)) 3 ~?= figura 6,
  cambiarBas (==3) (figura . (*2)) 4 ~?= figura 4,
  cambiarBas (==3) (figura . (*2)) 5 ~?= figura 5
  ]
{- main :: IO ()
main = void $ runTestTT testCambiarBas -}


testCambiar :: Test
testCambiar = test [
  cambiar (==3) (figura . (*2)) (figura 3) ~?= figura 6,
  cambiar (==3) (figura . (*2)) (figura 4) ~?= figura 4,
  cambiar (==3) (figura . (*2)) (figura 5) ~?= figura 5
  ]
--main :: IO ()
--main = void $ runTestTT testCambiar


testAnyDib :: Test
testAnyDib = test [
-- Test con dibujos que contienen el valor buscado.
  anyDib even (figura 2) ~?= True, --even se fija si es par entonces devuelve true
  anyDib (>3) (juntar 1 1(figura 2) (figura 3)) ~?= False,
-- Test con dibujos que no contienen el valor buscado.
  anyDib odd (figura 2) ~?= False, --odd se fija si es impar entonces devuelve true
  anyDib (<0) (apilar 1 1 (figura 2) (figura 3)) ~?= False
  ]
{- main :: IO ()
main = void $ runTestTT testAnyDib -}


testAllDib :: Test
testAllDib = test [
-- Test con dibujos que contienen el valor buscado.
  allDib even (figura 2) ~?= True, --even se fija si es par entonces devuelve true
  allDib (>3) (juntar 1 1(figura 2) (figura 3)) ~?= False,
-- Test con dibujos que no contienen el valor buscado.
  allDib odd (figura 2) ~?= False, --odd se fija si es impar entonces devuelve true
  allDib (<0) (apilar 1 1 (figura 2) (figura 3)) ~?= False
  ]
{- main :: IO ()
main = void $ runTestTT testAllDib   -}


testAndP :: Test
testAndP = "andP" ~: test [
    andP (>0) (>5) 6 ~?= True,
    andP (>0) (>5) 4 ~?= False,
    andP even odd 5 ~?= False,
    andP even odd 4 ~?= False
  ]
{- main :: IO ()
main = void $ runTestTT testAndP -}


testOrP :: Test
testOrP = test
  [ "orP True True" ~: orP (\_ -> True) (\_ -> True) () ~?=  True
  , "orP True False" ~: orP (\_ -> True) (\_ -> False) () ~?=  True
  , "orP False True" ~: orP (\_ -> False) (\_ -> True) () ~?=  True
  , "orP False False" ~: orP (\_ -> False) (\_ -> False) () ~?=  False
  ]

main :: IO Counts
main = runTestTT testOrP
 
