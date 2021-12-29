(ns tic-tac-toe.gui.pages.new-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.pages.new-game :refer :all]))

(def new-game-state
  {:new-game {:size 3
              :difficulty :easy
              :mode :player-vs-computer}})

(describe "new game"
  (it "does not create game when there is no size"
    (should= false (can-create-game? (assoc-in new-game-state [:new-game :size] nil))))
  (it "does not create game when there is no difficulty for player vs computer"
    (should= false (can-create-game? (assoc-in new-game-state [:new-game :difficulty] nil))))
  (it "does not create game when there is no game-mode"
    (should= false (can-create-game? (assoc-in new-game-state [:new-game :mode] nil))))
  (it "creates game with size 3, game mode player vs player, and no difficulty"
    (should= true (can-create-game? (assoc-in (assoc-in new-game-state [:new-game :difficulty] nil)
                                          [:new-game :mode] :player-vs-player))))
  (it "creates game with size 3, difficulty :easy, and game mode player vs computer"
    (should= true (can-create-game? new-game-state))))