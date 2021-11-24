(ns tic-tac-toe.player.unbeatable-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.player.player :refer :all]
            [tic-tac-toe.player.unbeatable-ai :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def bot (->unbeatable-ai \X \O))

(describe "->unbeatable-ai"
  (it "Hard bot is associated with the token it was given"
    (should= \X (:token (->unbeatable-ai \X \O)))
    (should= \O (:token (->unbeatable-ai \O \X)))
    (should= 321 (:token (->unbeatable-ai 321 \O))))
  (it "Hard bot holds the opponent's token"
    (should= \O (:opponent (->unbeatable-ai \X \O)))
    (should= \X (:opponent (->unbeatable-ai \O \X)))))

(describe "next-move"
  (it "Empty board results in corner square"
    (should= true (contains? #{[0 0] [0 2] [2 0] [2 2]} (next-move bot (->board [nil \X \X])))))
  (it "Returns last cell to complete row"
    (should= [0 0] (next-move bot (->board [nil \X \X])))
    (should= [0 1] (next-move bot (->board [\X nil \X])))
    (should= [0 2] (next-move bot (->board [\X \X])))
    (should= [1 0] (next-move bot (->board [nil nil nil nil \X \X])))
    (should= [1 0] (next-move bot (->board [\O \O nil nil \X \X])))
    (should= [1 1] (next-move bot (->board [nil nil nil \X nil \X])))
    (should= [1 2] (next-move bot (->board [nil nil nil \X \X])))
    (should= [2 0] (next-move bot (->board (concat (repeat 7 nil) [\X \X]))))
    (should= [2 1] (next-move bot (->board (concat (repeat 6 nil) [\X nil \X]))))
    (should= [2 2] (next-move bot (->board (concat (repeat 6 nil) [\X \X])))))

  (it "Returns last cell to complete col"
    (should= [0 0] (next-move bot (->board [nil nil nil \X nil nil \X])))
    (should= [0 1] (next-move bot (->board [nil nil nil nil \X nil nil \X])))
    (should= [0 2] (next-move bot (->board [nil nil nil nil nil \X nil nil\X])))
    (should= [1 0] (next-move bot (->board [\X nil nil nil nil nil \X])))
    (should= [1 1] (next-move bot (->board [nil \X nil nil nil nil nil \X])))
    (should= [1 2] (next-move bot (->board [nil nil \X nil nil nil nil nil \X])))
    (should= [1 2] (next-move bot (->board [nil \O \X nil nil nil nil \O \X])))
    (should= [2 0] (next-move bot (->board [\X nil nil \X])))
    (should= [2 1] (next-move bot (->board [nil \X nil nil \X])))
    (should= [2 2] (next-move bot (->board [nil nil \X nil nil \X]))))

  (it "Returns last cell to complete top-left diagonal"
    (should= [0 0] (next-move bot (->board [nil nil nil nil \X nil nil nil \X])))
    (should= [1 1] (next-move bot (->board (concat [\X] (repeat 7 nil) [\X]))))
    (should= [2 2] (next-move bot (->board [\X nil nil nil \X]))))

  (it "Returns last cell to complete top-right diagonal"
    (should= [0 2] (next-move bot (->board [nil nil nil nil \X nil \X])))
    (should= [1 1] (next-move bot (->board [nil nil \X nil nil nil \X])))
    (should= [2 0] (next-move bot (->board [nil nil \X nil \X])))))
