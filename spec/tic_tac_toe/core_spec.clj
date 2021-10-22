(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def empty-board (->board (repeat nil)))

(describe "valid-move?"
  (it "Empty vector results in false"
    (should= false (valid-move? empty-board [])))
  (it "Vector with one element results in false"
    (should= false (valid-move? empty-board [0])))
  (it "Vector with three elements results in false"
    (should= false (valid-move? empty-board [0 0 0])))
  (it "[0 0] results in true"
    (should= true (valid-move? empty-board [0 0])))
  (it "[1 1] results in true"
    (should= true (valid-move? empty-board [1 1])))
  (it "[3 2] results in false"
    (should= false (valid-move? empty-board [3 2])))
  (it "[3 3] results in false"
    (should= false (valid-move? empty-board [3 3])))
  (it "[2 3] results in false"
    (should= false (valid-move? empty-board [2 3])))
  (it "[4 2] results in false"
    (should= false (valid-move? empty-board [4 2])))
  (it "[2 4] results in false"
    (should= false (valid-move? empty-board [2 4])))
  (it "[-1 0] results in false"
    (should= false (valid-move? empty-board [-1 0])))
  (it "[0 -1] results in false"
    (should= false (valid-move? empty-board [0 -1])))
  (it "[0 0] results in false for empty vector"
    (should= false (valid-move? [] [0 0])))
  (it "[0 0] results in false when the position is already filled"
    (should= false (valid-move? (->board (concat [1] (repeat nil))) [0 0]))))

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
