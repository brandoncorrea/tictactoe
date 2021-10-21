(ns tic-tac-toe.game-board-formatter-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game-board :refer :all]
            [tic-tac-toe.game-board-formatter :refer :all]))

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
