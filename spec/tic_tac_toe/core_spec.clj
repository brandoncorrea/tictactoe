(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(describe "parse-input"
  (it "Empty input results in an empty array"
    (should= [] (parse-input "")))
  (it "One number results in an array with that number"
    (should= [1] (parse-input "1")))
  (it "Two numbers results in an array with both numbers"
    (should= [1 2] (parse-input "1 2")))
  (it "Non-numeric characters are ignored"
    (should= [1 2] (parse-input "1a2")))
  (it "Two-digit indices are split as two-digit numbers"
    (should= [10 11] (parse-input "10_11")))
  (it "Negative numbers are parsed as negative"
    (should= [-1 2] (parse-input "-1 2")))
  (it "Numbers joined by dashes are treated as negative numbers"
    (should= [1 -2 -3] (parse-input "1-2-3"))))
