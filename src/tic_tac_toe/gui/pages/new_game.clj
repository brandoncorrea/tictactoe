(ns tic-tac-toe.gui.pages.new-game
  (:require [tic-tac-toe.gui.pages.page :as p]
            [tic-tac-toe.gui.components.button :as b]
            [tic-tac-toe.gui.components.header :as h]
            [tic-tac-toe.gui.components.component :as c]
            [tic-tac-toe.gui.state :as s]))

(def ^:private header (h/->header "New Game"))

(def size-components
  [(b/->button "3x3" 20 150 200 200 (partial s/set-game-size 3))
   (b/->button "4x4" 280 150 200 200 (partial s/set-game-size 4))])

(def game-mode-components
  [(b/->button "Player vs Player" 20 150 200 200 (partial s/set-game-mode :player-vs-player))
   (b/->button "Player vs Computer" 280 150 200 200 (partial s/set-game-mode :player-vs-computer))])

(def difficulty-components
  [(b/->button "Easy" 20 150 133 133 (partial s/set-game-difficulty :easy))
   (b/->button "Medium" 183 150 133 133 (partial s/set-game-difficulty :medium))
   (b/->button "Hard" 346 150 133 133 (partial s/set-game-difficulty :hard))])

(defn requires-difficulty? [state]
  (and (s/player-vs-computer? state)
       (not (s/difficulty state))))

(defn dispatch-components [state]
  (cond (not (s/game-size state)) size-components
        (not (s/game-mode state)) game-mode-components
        (requires-difficulty? state) difficulty-components
        :else []))

(defmethod p/render-page :new-game [state]
  (c/draw header state)
  (c/draw-all (dispatch-components state) state))

(defmethod p/update-page :new-game [state]
  (c/update-all (dispatch-components state) state))
