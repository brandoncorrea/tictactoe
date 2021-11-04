(ns tic-tac-toe.player.unbeatable-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.player.player :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def bot-2 (->hard-bot \X \O))

(describe "->hard-bot"
  (it "Hard bot is associated with the token it was given"
    (should= \X (:token (->hard-bot \X \O)))
    (should= \O (:token (->hard-bot \O \X)))
    (should= 321 (:token (->hard-bot 321 \O))))
  (it "Hard bot holds the opponent's token"
    (should= \O (:opponent (->hard-bot \X \O)))
    (should= \X (:opponent (->hard-bot \O \X)))))

(describe "make-move"
  (it "Empty board results in corner square"
    (should= true (contains? #{[0 0] [0 2] [2 0] [2 2]} (next-move bot-2 (->board [nil \X \X])))))
  (it "Returns last cell to complete row"
    (should= [0 0] (next-move bot-2 (->board [nil \X \X])))
    (should= [0 1] (next-move bot-2 (->board [\X nil \X])))
    (should= [0 2] (next-move bot-2 (->board [\X \X])))
    (should= [1 0] (next-move bot-2 (->board [nil nil nil nil \X \X])))
    (should= [1 0] (next-move bot-2 (->board [\O \O nil nil \X \X])))
    (should= [1 1] (next-move bot-2 (->board [nil nil nil \X nil \X])))
    (should= [1 2] (next-move bot-2 (->board [nil nil nil \X \X])))
    (should= [2 0] (next-move bot-2 (->board (concat (repeat 7 nil) [\X \X]))))
    (should= [2 1] (next-move bot-2 (->board (concat (repeat 6 nil) [\X nil \X]))))
    (should= [2 2] (next-move bot-2 (->board (concat (repeat 6 nil) [\X \X])))))

  (it "Returns last cell to complete col"
    (should= [0 0] (next-move bot-2 (->board [nil nil nil \X nil nil \X])))
    (should= [0 1] (next-move bot-2 (->board [nil nil nil nil \X nil nil \X])))
    (should= [0 2] (next-move bot-2 (->board [nil nil nil nil nil \X nil nil\X])))
    (should= [1 0] (next-move bot-2 (->board [\X nil nil nil nil nil \X])))
    (should= [1 1] (next-move bot-2 (->board [nil \X nil nil nil nil nil \X])))
    (should= [1 2] (next-move bot-2 (->board [nil nil \X nil nil nil nil nil \X])))
    (should= [1 2] (next-move bot-2 (->board [nil \O \X nil nil nil nil \O \X])))
    (should= [2 0] (next-move bot-2 (->board [\X nil nil \X])))
    (should= [2 1] (next-move bot-2 (->board [nil \X nil nil \X])))
    (should= [2 2] (next-move bot-2 (->board [nil nil \X nil nil \X]))))

  (it "Returns last cell to complete top-left diagonal"
    (should= [0 0] (next-move bot-2 (->board [nil nil nil nil \X nil nil nil \X])))
    (should= [1 1] (next-move bot-2 (->board (concat [\X] (repeat 7 nil) [\X]))))
    (should= [2 2] (next-move bot-2 (->board [\X nil nil nil \X]))))

  (it "Returns last cell to complete top-right diagonal"
    (should= [0 2] (next-move bot-2 (->board [nil nil nil nil \X nil \X])))
    (should= [1 1] (next-move bot-2 (->board [nil nil \X nil nil nil \X])))
    (should= [2 0] (next-move bot-2 (->board [nil nil \X nil \X])))))
