(ns tic-tac-toe.player.easy-bot-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.player.player :refer :all]
            [tic-tac-toe.player.easy-bot :refer :all]
            [tic-tac-toe.game-board :as game]))

(def bot (->EasyBot \X))

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

(describe "token"
  (it "Results in the token it was initialized with"
    (should= \X (token (->EasyBot \X)))
    (should= \O (token (->EasyBot \O)))
    (should= 123 (token (->EasyBot 123)))))
