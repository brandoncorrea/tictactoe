(ns tic-tac-toe.player.unbeatable-ai-spec
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

  (for [[cells expected] [[{[0 0] \X [0 1] \X} [0 2]]
                          [{[0 0] \O [0 1] \O [1 1] \X [1 2] \X} [1 0]]
                          [{[0 0] \X [0 2] \X} [0 1]]
                          [{[0 0] \X [1 0] \X} [2 0]]
                          [{[0 0] \X [1 1] \X} [2 2]]
                          [{[0 0] \X [2 0] \X} [1 0]]
                          [{[0 0] \X [2 2] \X} [1 1]]
                          [{[0 1] \O [0 2] \X [2 1] \O [2 2] \X} [1 2]]
                          [{[0 1] \X [0 2] \X} [0 0]]
                          [{[0 1] \X [1 1] \X} [2 1]]
                          [{[0 1] \X [2 1] \X} [1 1]]
                          [{[0 2] \X [1 1] \X} [2 0]]
                          [{[0 2] \X [1 2] \X} [2 2]]
                          [{[0 2] \X [2 0] \X} [1 1]]
                          [{[0 2] \X [2 2] \X} [1 2]]
                          [{[1 0] \X [1 1] \X} [1 2]]
                          [{[1 0] \X [1 2] \X} [1 1]]
                          [{[1 0] \X [2 0] \X} [0 0]]
                          [{[1 1] \X [1 2] \X} [1 0]]
                          [{[1 1] \X [2 0] \X} [0 2]]
                          [{[1 1] \X [2 1] \X} [0 1]]
                          [{[1 1] \X [2 2] \X} [0 0]]
                          [{[1 2] \X [2 2] \X} [0 2]]
                          [{[2 0] \X [2 1] \X} [2 2]]
                          [{[2 0] \X [2 2] \X} [2 1]]
                          [{[2 1] \X [2 2] \X} [2 0]]]]
    (it (format "Board with cells %s results in %s" cells expected)
      (should= expected (next-move bot (merge (->board []) cells))))))
