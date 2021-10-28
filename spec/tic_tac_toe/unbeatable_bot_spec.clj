(ns tic-tac-toe.unbeatable-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.unbeatable-bot :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(describe "request-move"
  (it "Empty board results in corner square"
    (should= true (contains? #{[0 0] [0 2] [2 0] [2 2]} (request-move (->board [nil \X \X]) \X \O))))
  (it "Returns last cell to complete row"
    (should= [0 0] (request-move (->board [nil \X \X]) \X \O))
    (should= [0 1] (request-move (->board [\X nil \X]) \X \O))
    (should= [0 2] (request-move (->board [\X \X]) \X \O))
    (should= [1 0] (request-move (->board [nil nil nil nil \X \X]) \X \O))
    (should= [1 0] (request-move (->board [\O \O nil nil \X \X]) \X \O))
    (should= [1 1] (request-move (->board [nil nil nil \X nil \X]) \X \O))
    (should= [1 2] (request-move (->board [nil nil nil \X \X]) \X \O))
    (should= [2 0] (request-move (->board (concat (repeat 7 nil) [\X \X])) \X \O))
    (should= [2 1] (request-move (->board (concat (repeat 6 nil) [\X nil \X])) \X \O))
    (should= [2 2] (request-move (->board (concat (repeat 6 nil) [\X \X])) \X \O)))

  (it "Returns last cell to complete col"
    (should= [0 0] (request-move (->board [nil nil nil \X nil nil \X]) \X \O))
    (should= [0 1] (request-move (->board [nil nil nil nil \X nil nil \X]) \X \O))
    (should= [0 2] (request-move (->board [nil nil nil nil nil \X nil nil\X]) \X \O))
    (should= [1 0] (request-move (->board [\X nil nil nil nil nil \X]) \X \O))
    (should= [1 1] (request-move (->board [nil \X nil nil nil nil nil \X]) \X \O))
    (should= [1 2] (request-move (->board [nil nil \X nil nil nil nil nil \X]) \X \O))
    (should= [1 2] (request-move (->board [nil \O \X nil nil nil nil \O \X]) \X \O))
    (should= [2 0] (request-move (->board [\X nil nil \X]) \X \O))
    (should= [2 1] (request-move (->board [nil \X nil nil \X]) \X \O))
    (should= [2 2] (request-move (->board [nil nil \X nil nil \X]) \X \O)))

  (it "Returns last cell to complete top-left diagonal"
    (should= [0 0] (request-move (->board [nil nil nil nil \X nil nil nil \X]) \X \O))
    (should= [1 1] (request-move (->board (concat [\X] (repeat 7 nil) [\X])) \X \O))
    (should= [2 2] (request-move (->board [\X nil nil nil \X]) \X \O)))

  (it "Returns last cell to complete top-right diagonal"
    (should= [0 2] (request-move (->board [nil nil nil nil \X nil \X]) \X \O))
    (should= [1 1] (request-move (->board [nil nil \X nil nil nil \X]) \X \O))
    (should= [2 0] (request-move (->board [nil nil \X nil \X]) \X \O))))
