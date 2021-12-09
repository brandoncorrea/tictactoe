(ns tic-tac-toe.gui.pages.game-over
  (:require [tic-tac-toe.gui.components.header :as h]
            [tic-tac-toe.gui.components.component :as c]
            [tic-tac-toe.gui.components.button :as b]
            [tic-tac-toe.gui.pages.page :as p]
            [tic-tac-toe.gui.components.ttt-board :as t]
            [tic-tac-toe.gui.state :as s]
            [tic-tac-toe.gui.router :as r]))

(defn header-text [state]
  (if (s/draw? state)
    (str "Draw!")
    (str (s/winner state) " won!")))

(defn- dispatch-components [state]
  [t/board
   (h/->header (header-text state))
   (b/->button "New Game" 150 460 200 30 #(r/navigate % :new-game))])

(defmethod p/render-page :game-over [state]
  (c/draw-all (dispatch-components state) state))

(defmethod p/update-page :game-over [state]
  (c/update-all (dispatch-components state) state))
