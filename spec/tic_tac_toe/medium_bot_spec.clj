(ns tic-tac-toe.medium-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.medium-bot :refer :all]
            [tic-tac-toe.player :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def bot (->MediumBot \X \O))

(describe "->MediumBot"
  (it "Creates bot with associated token"
    (should= \X (token (->MediumBot \X \O)))
    (should= \O (token (->MediumBot \O \O)))
    (should= 321 (token (->MediumBot 321 \O)))))

(describe "next-move"
  (it "Chooses last square when it is the only available space"
    (should= [2 2] (next-move bot (->board (range 8)))))
  (it "Chooses first square when it is the only available space"
    (should= [0 0] (next-move bot (->board (concat [nil] (range 9))))))
  (it "Blocks opponent from winning winning on the next move"
    (should= [1 1] (next-move bot (->board [nil nil nil \O nil \O]))))
  (it "Chooses winning square when next move wins"
    (should= [1 0] (next-move bot (->board [\X nil nil nil nil nil \X]))))
  (it "First move does not optimize to center square"
    (should-not= [1 1] (next-move bot (->board [\O])))))
