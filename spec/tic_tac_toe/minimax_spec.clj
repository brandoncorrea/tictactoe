(ns tic-tac-toe.minimax-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.minimax :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def empty-board (->board []))
(defn winner [board] (:winner (game-results board)))

(describe "children"
  (it "Empty board results in all cells"
    (should= (set (map #(assoc empty-board % \X)
                       [[0 0] [0 1] [0 2]
                        [1 0] [1 1] [1 2]
                        [2 0] [2 1] [2 2]]))
             (set (children (->board []) \X))))
  (it "Full board should result in no cells"
    (should= [] (children (->board (range 10)) \X))))

(describe "terminal?"
  (it "Full board is terminal"
    (should= true (terminal? (->board (range 10)))))
  (it "Empty board is not terminal"
    (should= false (terminal? (->board []))))
  (it "Board with a winner is terminal"
    (should= true (terminal? (->board [1 1 1])))))

(describe "value-of"
  (it "Draw game results in zero"
    (should= 0 (value-of (winner (->board (range 10))) \X 0)))
  (it "Draw game results in zero at any depth"
    (should= 0 (value-of (winner (->board (range 10))) \X 5)))
  (it "Winning game results in one"
    (should= 1 (value-of (winner (->board [\X \X \X])) \X 0)))
  (it "Losing game results in negative one"
    (should= -1 (value-of (winner (->board [\O \O \O])) \X 0)))
  (it "Winning board results in 1/2 with a depth of 1"
    (should= 1/2 (value-of (winner (->board [\X \X \X])) \X 1)))
  (it "Winning board results in 1/4 with a depth of 2"
    (should= 1/4 (value-of (winner (->board [\X \X \X])) \X 2)))
  (it "Losing board results in -1/2 with a depth of 1"
    (should= -1/2 (value-of (winner (->board [\O \O \O])) \X 1)))
  (it "Losing board results in -1/4 with a depth of 2"
    (should= -1/4 (value-of (winner (->board [\O \O \O])) \X 2))))

(describe "minimax"
  (it "Results in 1 for winning board"
    (should= 1 (minimax (->board [\X \X \X]) 0 \X [\X \O])))
  (it "Results in 1/2 for almost-winning board"
    (should= 1/2 (minimax (->board [\X \X]) 0 \X [\X \O])))
  (it "Empty board results in 1/32 for five moves to draw"
    (should= 1/32 (minimax (->board []) 0 \X [\X \O])))
  (it "Losing board results in negative one"
    (should= true (> 0 (minimax (->board [\O \O nil \O]) 0 \X [\X \O])))))

(describe "optimal-move"
  (it "Results in empty cell for two in row"
    (should= [0 0] (optimal-move (->board [nil \X \X]) \X \O))))