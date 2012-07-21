module Main where

import System.ZMQ
import Control.Monad
import Data.MessagePack.Unpack

import Control.Applicative

tickEndpoint = "ipc:///var/tmp/ib"

data Tick = Tick String TickField Double deriving Show

data TickField = Bid | Ask | Last | High | Low | Close | Open | Unknown Int deriving Show

instance Unpackable TickField where
  get = get >>= \n -> return $ case n :: Int of
    1 -> Bid
    2 -> Ask
    4 -> Last
    6 -> High
    7 -> Low
    9 -> Close
    14 -> Open
    _ -> Unknown n

instance Unpackable Tick where
  get = do (tickerid, field, price) <- get
           return $ Tick tickerid field price

processTick :: Socket Sub -> IO ()
processTick s = do msg <- receive s []
                   print $ (unpack msg :: Tick)

main :: IO ()
main = withContext 1 $ \ctx -> 
         withSocket ctx Sub $ \s -> do
           connect s tickEndpoint
           subscribe s ""
           forever $ processTick s
