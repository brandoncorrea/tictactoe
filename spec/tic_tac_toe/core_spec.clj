(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def empty-board (->board (repeat 0)))

(describe "format-board"
  (it "Formats empty vector"
    (should= "" (format-board [])))
  (it "Formats 2D empty vectors"
    (should= "[]\n[]" (format-board [[] []])))
  (it "Formats 2D vector with one value"
    (should= "[1]" (format-board [[1]])))
  (it "Formats 2D vector with two values"
    (should= "[1 2]" (format-board [[1 2]])))
  (it "Formats 2D vector with two rows"
    (should= "[1 2]\n[3 4]" (format-board [[1 2] [3 4]])))
  (it "Formats 3x3 Board"
    (should= "[1 2 3]\n[4 5 6]\n[7 8 9]" (format-board (->board (range 1 10))))))

(describe "valid-move?"
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
    (should= false (valid-move? (->board (concat [1] (repeat 0))) [0 0]))))

(describe "parse-player-choice"
  (it "Parses a choice? idk man fix this test."
    ))