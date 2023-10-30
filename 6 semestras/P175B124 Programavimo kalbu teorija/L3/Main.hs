fun :: String -> Integer
fun x = abs $ (((read x * 63) + 7492) * 5) - 498

tens :: Integer -> Integer
tens x = (x `div` 10) `mod` 10

calcAndPrint :: [String] -> IO ()
calcAndPrint [] = return ()
calcAndPrint (x:xs) = do
    putStrLn $ show $ tens (fun x)
    calcAndPrint xs

main :: IO ()
main = do
    content <- readFile "input.txt"
    calcAndPrint (tail (lines content))
