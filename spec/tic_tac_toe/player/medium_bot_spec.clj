(ns tic-tac-toe.player.medium-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.player.player :refer :all]
            [tic-tac-toe.player.medium-bot :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def bot (->medium-bot \X \O))

(describe "->medium-bot"
  (it "Medium bot is associated with the token it was given"
    (should= \X (:token (->medium-bot \X \O)))
    (should= \O (:token (->medium-bot \O \X)))
    (should= 321 (:token (->medium-bot 321 \O))))
  (it "Medium bot holds the opponent's token"
    (should= \O (:opponent (->medium-bot \X \O)))
    (should= \X (:opponent (->medium-bot \O \X)))))

(describe "next-move"
  (for [[cell init] [[[2 2] (range 8)]
                     [[0 0] (concat [nil] (range 9))]
                     [[1 1] [nil nil nil \O nil \O]]
                     [[1 0] [\X nil nil nil nil nil \X]]]]
    (it (format "Chooses %s with initial values: %s" cell init)
      (should= cell (next-move bot (->board init)))))
  (it "First move does not optimize to center square"
    (should-not= [1 1] (next-move bot (->board [\O])))))
