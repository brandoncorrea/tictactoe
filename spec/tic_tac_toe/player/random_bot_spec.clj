(ns tic-tac-toe.player.random-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.player.player :refer :all]
            [tic-tac-toe.player.random-bot :refer :all]
            [tic-tac-toe.game-board :as game]))

(def bot (->random-bot \X))

(describe "->easy-bot"
  (it "Easy bot contains the token it was given"
    (should= \X (:token (->random-bot \X)))
    (should= \O (:token (->random-bot \O)))
    (should= 123 (:token (->random-bot 123))))
  (it "Easy bot is of type :easy"
    (should= :random (:type (->random-bot nil)))))

(describe "next-move"
  (it "Next move results in last available cell"
    (should= [2 2] (next-move bot (game/->board (range 8)))))
  (it "Next move results in either of the last two cells"
    (should-contain (next-move bot (game/->board (range 7)))
                    [[2 1] [2 2]]))
  (it "Next move results in one of the first three empty cells"
    (should-contain (next-move bot (game/->board [nil nil nil 1 2 3 4 5 6]))
                    [[0 0] [0 1] [0 2]]))
  (it "Empty board result in any random cell on the board"
    (should-contain (next-move bot (game/->board []))
                    [[0 0] [0 1] [0 2]
                     [1 0] [1 1] [1 2]
                     [2 0] [2 1] [2 2]])))