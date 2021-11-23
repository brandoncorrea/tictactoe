(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.player.player :refer :all]
            [tic-tac-toe.player.random-bot :refer :all]
            [tic-tac-toe.player.medium-bot :refer :all]
            [tic-tac-toe.player.unbeatable-bot :refer :all]))

(describe "->bot"
  (it ":easy difficulty results in an easy bot"
    (should= (->random-bot \X) (->bot :easy \O \X))
    (should= (->random-bot \O) (->bot :easy \X \O))
    (should= (->random-bot 456) (->bot :easy 123 456)))
  (it ":medium difficulty results in a medium bot"
    (should= (->medium-bot \X \O) (->bot :medium \O \X))
    (should= (->medium-bot \O \X) (->bot :medium \X \O))
    (should= (->medium-bot 456 123) (->bot :medium 123 456)))
  (it ":hard difficulty results in a hard bot"
    (should= (->unbeatable-bot \X \O) (->bot :hard \O \X))
    (should= (->unbeatable-bot \O \X) (->bot :hard \X \O))
    (should= (->unbeatable-bot 456 123) (->bot :hard 123 456)))
  (it "default bot is hard difficulty"
    (should= (->unbeatable-bot \X \O) (->bot nil \O \X))))
