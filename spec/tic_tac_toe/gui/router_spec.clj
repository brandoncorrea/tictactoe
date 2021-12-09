(ns tic-tac-toe.gui.router-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.router :refer :all]))

(def new-game-state
  {:page :new-game
   :new-game {:size 3
              :difficulty :easy
              :game-mode :player-vs-computer}})

(describe "router"
  (for [page [:new-game :game-over]]
    (it (format "navigates to %s" page)
      (should= {:page page} (navigate {} page))))

  (it "does not create game when the :new-game page is inactive"
    (should= false (create-game? (assoc new-game-state :page :play))))
  (it "does not create game when there is no size"
    (should= false (create-game? (assoc-in new-game-state [:new-game :size] nil))))
  (it "does not create game when there is no difficulty for player vs computer"
    (should= false (create-game? (assoc-in new-game-state [:new-game :difficulty] nil))))
  (it "does not create game when there is no game-mode"
    (should= false (create-game? (assoc-in new-game-state [:new-game :game-mode] nil))))
  (it "creates game with size 3, game mode player vs player, and no difficulty"
    (should= true (create-game? (assoc-in (assoc-in new-game-state [:new-game :difficulty] nil)
                                          [:new-game :game-mode] :player-vs-player))))
  (it "creates game with size 3, difficulty :easy, and game mode player vs computer"
    (should= true (create-game? new-game-state)))

  (it "displays game over when the page is set to :game-over"
    (should= true (show-game-over? {:page :game-over})))
  (it "does not display game over when the page is set to :new-game"
    (should= false (show-game-over? {:page :new-game})))
  (it "does not display game over when game is in play and results do not show game-over"
    (should= false (show-game-over? {:page :play :play {:results {:game-over false}}})))
  (it "displays game over when the page is play and the game board results in a :game-over result"
    (should= true (show-game-over? {:page :play :play {:results {:game-over true}}}))))
