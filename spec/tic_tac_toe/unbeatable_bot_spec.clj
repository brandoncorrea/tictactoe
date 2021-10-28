(ns tic-tac-toe.unbeatable-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.unbeatable-bot :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(describe "has-two-tokens?"
  (it "Empty row results in false"
    (should= false (has-two-tokens? \X {[0 0] nil [0 1] nil [0 2] nil})))
  (it "Row with two empty cells results in false"
    (should= false (has-two-tokens? \X {[0 0] \X [0 1] nil [0 2] nil})))
  (it "Row with one empty cell results in true"
    (should= true (has-two-tokens? \X {[0 0] \X [0 1] \X [0 2] nil}))))

(describe "request-move"
  (it "Empty board results in first square"
    (should= [0 0] (request-move \X (->board []))))
  (it "Returns last cell to complete row"
    (should= [0 0] (request-move \X (->board [nil \X \X])))
    (should= [0 1] (request-move \X (->board [\X nil \X])))
    (should= [0 2] (request-move \X (->board [\X \X])))
    (should= [1 0] (request-move \X (->board [nil nil nil nil \X \X])))
    (should= [1 0] (request-move \X (->board [\O \O nil nil \X \X])))
    (should= [1 1] (request-move \X (->board [nil nil nil \X nil \X])))
    (should= [1 2] (request-move \X (->board [nil nil nil \X \X])))
    (should= [2 0] (request-move \X (->board (concat (repeat 7 nil) [\X \X]))))
    (should= [2 1] (request-move \X (->board (concat (repeat 6 nil) [\X nil \X]))))
    (should= [2 2] (request-move \X (->board (concat (repeat 6 nil) [\X \X])))))

  (it "Returns last cell to complete col"
    (should= [0 0] (request-move \X (->board [nil nil nil \X nil nil \X])))
    (should= [0 1] (request-move \X (->board [nil nil nil nil \X nil nil \X])))
    (should= [0 2] (request-move \X (->board [nil nil nil nil nil \X nil nil\X])))
    (should= [1 0] (request-move \X (->board [\X nil nil nil nil nil \X])))
    (should= [1 1] (request-move \X (->board [nil \X nil nil nil nil nil \X])))
    (should= [1 2] (request-move \X (->board [nil nil \X nil nil nil nil nil \X])))
    (should= [1 2] (request-move \X (->board [nil \O \X nil nil nil nil \O \X])))
    (should= [2 0] (request-move \X (->board [\X nil nil \X])))
    (should= [2 1] (request-move \X (->board [nil \X nil nil \X])))
    (should= [2 2] (request-move \X (->board [nil nil \X nil nil \X]))))

  (it "Returns last cell to complete top-left diagonal"
    (should= [0 0] (request-move \X (->board [nil nil nil nil \X nil nil nil \X])))
    (should= [1 1] (request-move \X (->board (concat [\X] (repeat 7 nil) [\X]))))
    (should= [2 2] (request-move \X (->board [\X nil nil nil \X]))))

  (it "Returns last cell to complete top-right diagonal"
    (should= [0 2] (request-move \X (->board [nil nil nil nil \X nil \X])))
    (should= [1 1] (request-move \X (->board [nil nil \X nil nil nil \X])))
    (should= [2 0] (request-move \X (->board [nil nil \X nil \X])))))