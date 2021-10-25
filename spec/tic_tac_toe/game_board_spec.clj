(ns tic-tac-toe.game-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def empty-board [[nil nil nil] [nil nil nil] [nil nil nil]])
(def game-over-win {:draw false :winner 1})
(def game-over-draw {:draw true :winner nil})

(describe "game-board"
  (describe "->board"
    (it "Partitions 3x3 empty board"
      (should= empty-board (->board (repeat nil))))
    (it "Partitions 3x3 board with all 1s"
      (should= [[1 1 1] [1 1 1] [1 1 1]]
               (->board (repeat 1))))
    (it "Board is returned as a vector"
      (should= true (vector? (->board (repeat 0)))))
    (it "Rows are returned as vectors"
      (should= true (every? vector? (->board (repeat 0))))))

  (describe "mark-square"
    (it "Player 1 marks the first square"
      (should= (->board (concat [1] (repeat nil)))
               (mark-square empty-board [0 0] 1)))
    (it "Player 2 marks the second square"
      (should= (->board (concat [nil 2] (repeat nil)))
               (mark-square empty-board [0 1] 2))))

  (describe "game-results"
    (it "Empty game board results in nil"
      (should= nil (game-results empty-board)))
    (it "Board with one filled square results in nil"
      (should= nil (game-results (->board (concat [1] (repeat nil))))))
    (it "Board with first three items as 1 results in a win"
      (should= game-over-win (game-results (->board (concat [1 1 1] (repeat 0))))))
    (it "Board with middle three items as 1 results in a win"
      (should= game-over-win (game-results (->board [nil nil nil 1 1 1 nil nil nil]))))
    (it "Board with last three items as 1 results in a win"
      (should= game-over-win (game-results (->board (concat (repeat 6 nil) [1 1 1])))))
    (it "Board with first row filled with different values results in nil"
      (should= nil (game-results (->board (concat [1 2 1] (repeat nil))))))
    (it "Board with first column filled results in a win"
      (should= game-over-win (game-results (->board [1 nil nil 1 nil nil 1 nil nil]))))
    (it "Board with second column filled results in a win"
      (should= game-over-win (game-results (->board [nil 1 nil nil 1 nil nil 1 nil]))))
    (it "Board with last column filled results in a win"
      (should= game-over-win (game-results (->board [nil nil 1 nil nil 1 nil nil 1]))))
    (it "Board with top-left diagonal filled results in a win"
      (should= game-over-win (game-results (->board [1 nil nil nil 1 nil nil nil 1]))))
    (it "Board with top-right diagonal filled results in a win"
      (should= game-over-win (game-results (->board [nil nil 1 nil 1 nil 1 nil nil]))))
    (it "Full game board results in a draw"
      (should= game-over-draw (game-results (->board (range 1 10)))))))
