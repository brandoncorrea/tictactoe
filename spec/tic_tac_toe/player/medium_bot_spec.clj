(ns tic-tac-toe.player.medium-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.player.player :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def bot-2 (->medium-bot \X \O))

(describe "->medium-bot"
  (it "Medium bot is associated with the token it was given"
    (should= \X (:token (->medium-bot \X \O)))
    (should= \O (:token (->medium-bot \O \X)))
    (should= 321 (:token (->medium-bot 321 \O))))
  (it "Medium bot holds the opponent's token"
    (should= \O (:opponent (->medium-bot \X \O)))
    (should= \X (:opponent (->medium-bot \O \X)))))

(describe "make-move"
  (it "Chooses last square when it is the only available space"
    (should= [2 2] (next-move bot-2 (->board (range 8)))))
  (it "Chooses first square when it is the only available space"
    (should= [0 0] (next-move bot-2 (->board (concat [nil] (range 9))))))
  (it "Blocks opponent from winning winning on the next move"
    (should= [1 1] (next-move bot-2 (->board [nil nil nil \O nil \O]))))
  (it "Chooses winning square when next move wins"
    (should= [1 0] (next-move bot-2 (->board [\X nil nil nil nil nil \X]))))
  (it "First move does not optimize to center square"
    (should-not= [1 1] (next-move bot-2 (->board [\O])))))
