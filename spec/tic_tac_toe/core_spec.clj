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

(describe "format-row"
  (it "Formats empty row with brackets"
    (should= "[]" (format-row [])))
  (it "Formats single-element row"
    (should= "[1]" (format-row [1])))
  (it "Formats row with character"
    (should= "[X]" (format-row [\X])))
  (it "Formats row with string"
    (should= "[ABC]" (format-row ["ABC"])))
  (it "Formats empty cells with underscores"
    (should= "[X _ O]" (format-row [\X 0 \O]))))

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
    (should= false (valid-move? (->board (concat [1] (repeat 0))) [0 0]))))

(describe "parse-input"
  (it "Empty input results in an empty array"
    (should= [] (parse-input "")))
  (it "One number results in an array with that number"
    (should= [1] (parse-input "1")))
  (it "Two numbers results in an array with both numbers"
    (should= [1 2] (parse-input "1 2")))
  (it "Non-numeric characters are ignored"
    (should= [1 2] (parse-input "1-2")))
  (it "Two-digit indices are split as two-digit numbers"
    (should= [10 11] (parse-input "10-11"))))
