(ns tic-tac-toe.ui.game-board-formatter-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game-board :refer :all]
            [tic-tac-toe.ui.game-board-formatter :refer :all]))

(describe "format-board"
  (it "Formats empty 1x1 board"
    (should= "[_]" (format-board (->board (repeat nil) 1))))
  (it "Formats empty 2x2 board"
    (should= "[_ _]\n[_ _]" (format-board (->board (repeat nil) 2))))
  (it "Formats empty 3x3 board"
    (should= "[_ _ _]\n[_ _ _]\n[_ _ _]" (format-board (->board (repeat nil)))))
  (it "Formats 1x1 board with one value"
    (should= "[1]" (format-board (->board [1] 1))))
  (it "Formats 2x2 board with two values"
    (should= "[1 2]\n[_ _]" (format-board (->board [1 2] 2))))
  (it "Formats 2x2 board with four values"
    (should= "[1 2]\n[3 4]" (format-board (->board [1 2 3 4] 2))))
  (it "Formats 3x3 Board with nine values"
    (should= "[1 2 3]\n[4 5 6]\n[7 8 9]" (format-board (->board (range 1 10)))))
  (it "Characters don't include backslashes"
    (should= "[X O Y]\n[_ _ _]\n[_ _ _]" (format-board (->board [\X \O \Y]))))
  (it "Strings don't include quotation marks"
    (should= "[A B C]\n[_ _ _]\n[_ _ _]" (format-board (->board ["A" "B" "C"]))))
  (it "Formats 1x5 board"
    (should= "[1 _ 3 5 _]" (format-board (->board [1 nil 3 5] 5 1))))
  (it "Formats 3x3x3 board"
    (should= (str "[0 1 2]\t[9 10 11]\t[18 19 20]\n"
                  "[3 4 5]\t[12 13 14]\t[21 22 23]\n"
                  "[6 7 8]\t[15 16 17]\t[24 25 26]")
             (format-board (->board (range) 3 3)))
    (should= (str "[_ _ _]\t[_ _ _]\t[_ _ _]\n"
                  "[_ _ _]\t[_ _ _]\t[_ _ _]\n"
                  "[_ _ _]\t[_ _ _]\t[_ _ _]")
             (format-board (->board [] 3 3)))))

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
    (should= "[X _ O]" (format-row [\X nil \O]))))
