(ns tic-tac-toe.console.game-board-formatter-spec
  (:require [speclj.core :refer :all]
            [ttt-core.game-board :as b]
            [tic-tac-toe.console.formatter :refer :all]))

(describe "format-board"
  (for [[size expected] (vec {1 "[_]"
                              2 "[_ _]\n[_ _]"
                              3 "[_ _ _]\n[_ _ _]\n[_ _ _]"})]
    (it (format "Formats empty %dx%d board" size size)
      (should= expected (format-board (b/->board (repeat nil) size)))))

  (for [[size init expected] [[1 [1] "[1]"]
                              [2 [1 2] "[1 2]\n[_ _]"]
                              [2 [1 2 3 4] "[1 2]\n[3 4]"]
                              [3 (range 1 10) "[1 2 3]\n[4 5 6]\n[7 8 9]"]
                              [3 [\X \O \Y] "[X O Y]\n[_ _ _]\n[_ _ _]"]
                              [3 ["A" "B" "C"] "[A B C]\n[_ _ _]\n[_ _ _]"]]]
    (it (format "Formats %dx%d board with initial values: %s" size size init)
      (should= expected (format-board (b/->board init size)))))

  (it "Formats 1x5x1 board"
    (should= "[1 _ 3 5 _]" (format-board (b/->board [1 nil 3 5] 5 1))))

  (it "Formats 3x3x3 board"
    (should= (str "[0 1 2]\t[9 10 11]\t[18 19 20]\n"
                  "[3 4 5]\t[12 13 14]\t[21 22 23]\n"
                  "[6 7 8]\t[15 16 17]\t[24 25 26]")
             (format-board (b/->board (range) 3 3)))
    (should= (str "[_ _ _]\t[_ _ _]\t[_ _ _]\n"
                  "[_ _ _]\t[_ _ _]\t[_ _ _]\n"
                  "[_ _ _]\t[_ _ _]\t[_ _ _]")
             (format-board (b/->board [] 3 3)))))

(describe "format-row"
  (for [[row expected] (vec {[] "[]" [1] "[1]" [\X] "[X]" ["ABC"] "[ABC]" [\X nil \O] "[X _ O]"})]
    (it (format "Formatting row %s results in %s" row expected)
      (should= expected (format-row row)))))
