(ns tic-tac-toe.game-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def empty-board [[0 0 0] [0 0 0] [0 0 0]])

(describe "game-board"
  (describe "->board"
    (it "Partitions 3x3 empty board"
      (should= empty-board (->board (repeat 0))))
    (it "Partitions 3x3 board with all 1s"
      (should= [[1 1 1] [1 1 1] [1 1 1]]
               (->board (repeat 1))))
    (it "Board is returned as a vector"
      (should= true (vector? (->board (repeat 0)))))
    (it "Rows are returned as vectors"
      (should= true (every? vector? (->board (repeat 0))))))

  (describe "mark-square"
    (it "Player 1 marks the first square"
      (should= [[1 0 0] [0 0 0] [0 0 0]]
               (mark-square empty-board [0 0] 1)))
    (it "Player 2 marks the second square"
      (should= [[0 2 0] [0 0 0] [0 0 0]]
               (mark-square empty-board [0 1] 2))))

  (describe "game-over?"
    (it "Empty game board results in false"
      (should= false (game-over? empty-board)))
    (it "Board with one filled square results in false"
      (should= false (game-over? (->board (concat [1] (repeat 0))))))
    (it "Board with first three items as 1 results in true"
      (should= true (game-over? (->board (concat [1 1 1] (repeat 0))))))
    (it "Board with middle three items as 1 results in true"
      (should= true (game-over? (->board [0 0 0 1 1 1 0 0 0]))))
    (it "Board with last three items as 1 results in true"
      (should= true (game-over? (->board [0 0 0 0 0 0 1 1 1]))))
    (it "Board with first row filled with different values results in false"
      (should= false (game-over? (->board (concat [1 2 1] (repeat 0))))))
    (it "Board with first column filled results in true"
      (should= true (game-over? (->board [1 0 0 1 0 0 1 0 0]))))
    (it "Board with second column filled results in true"
      (should= true (game-over? (->board [0 1 0 0 1 0 0 1 0]))))
    (it "Board with last column filled results in true"
      (should= true (game-over? (->board [0 0 1 0 0 1 0 0 1]))))
    (it "Board with top-left diagonal filled results in true"
      (should= true (game-over? (->board [1 0 0 0 1 0 0 0 1]))))
    (it "Board with top-right diagonal filled results in true"
      (should= true (game-over? (->board [0 0 1 0 1 0 1 0 0]))))))
