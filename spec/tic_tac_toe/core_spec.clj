(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def empty-board (->board (repeat nil)))
(def invalid-moves
  [[] [0] [0 0 0] [3 2] [3 3] [2 3] [4 2] [2 4] [-1 0] [0 -1]])

(def valid-moves
  [[0 0] [1 1] [0 1] [1 0]])

(describe "valid-move?"
  (it "Invalid moves result in false"
    (loop [[move & rest-moves] invalid-moves]
      (when move
        (should-not (valid-move? empty-board move))
        (recur rest-moves))))
  (it "Valid positions result in true for empty spaces and false for occupied spaces"
    (loop [[move & rest-moves] valid-moves]
      (when move
        (should (valid-move? empty-board move))
        (should-not (valid-move? (assoc-in empty-board move 1) move))
        (recur rest-moves))))
  (it "[0 0] results in false for empty vector"
    (should-not (valid-move? [] [0 0]))))

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
