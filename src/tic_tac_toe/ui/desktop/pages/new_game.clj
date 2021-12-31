(ns tic-tac-toe.ui.desktop.pages.new-game
  (:require [tic-tac-toe.ui.desktop.pages.page :as p]
            [tic-tac-toe.ui.desktop.components.toggle-button :as tb]
            [tic-tac-toe.ui.desktop.components.header :as h]
            [tic-tac-toe.ui.desktop.components.component :as c]
            [tic-tac-toe.ui.desktop.state :as s]))

(def ^:private header (h/->header "New Game"))

(defn can-create-game? [state]
  (boolean (and (s/game-size state)
                (or (s/player-vs-player? state)
                    (and (s/difficulty state)
                         (s/game-mode state))))))

(defn continue-clicked [state]
  (if (can-create-game? state)
    (s/navigate (s/build-new-game state) :play)
    state))

(def components
  [header
   (tb/->toggle-button "3x3" 66 50 150 30
                       (partial s/set-game-size 3)
                       #(= 3 (s/game-size %)))
   (tb/->toggle-button "4x4" 284 50 150 30
                       (partial s/set-game-size 4)
                       #(= 4 (s/game-size %)))
   (tb/->toggle-button "Player vs Player" 66 100 150 30
                       (partial s/set-game-mode :player-vs-player)
                       s/player-vs-player?)
   (tb/->toggle-button "Player vs Computer" 284 100 150 30
                       (partial s/set-game-mode :player-vs-computer)
                       s/player-vs-computer?)
   (tb/->toggle-button "Easy" 66 150 100 30
                       (partial s/set-game-difficulty :easy)
                       #(= :easy (s/difficulty %)))
   (tb/->toggle-button "Medium" 200 150 100 30
                       (partial s/set-game-difficulty :medium)
                       #(= :medium (s/difficulty %)))
   (tb/->toggle-button "Hard" 334 150 100 30
                       (partial s/set-game-difficulty :hard)
                       #(= :hard (s/difficulty %)))
   (tb/->toggle-button "Continue" 66 200 368 50 continue-clicked can-create-game?)])

(defmethod p/render-page :new-game [state]
  (c/draw-all components state))

(defmethod p/update-page :new-game [state]
  (c/update-all components state))
