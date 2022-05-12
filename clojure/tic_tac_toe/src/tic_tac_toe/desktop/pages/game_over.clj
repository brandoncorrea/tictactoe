(ns tic-tac-toe.desktop.pages.game-over
  (:require [tic-tac-toe.desktop.components.header :as h]
            [tic-tac-toe.desktop.components.component :as c]
            [tic-tac-toe.desktop.components.button :as b]
            [tic-tac-toe.desktop.pages.page :as p]
            [tic-tac-toe.desktop.components.ttt-board :as t]
            [tic-tac-toe.desktop.state :as s]))

(defn header-text [state]
  (if (s/draw? state)
    (str "Draw!")
    (str (s/winner state) " won!")))

(defn- dispatch-components [{width :screen-width height :screen-height :as state}]
  [t/board
   (h/->header (header-text state))
   (b/->button "New Game"
               (- (/ width 2) 100)
               (- height 30 (/ height 50))
               200 30 #(s/navigate % :new-game))])

(defmethod p/render-page :game-over [state]
  (c/draw-all (dispatch-components state) state))

(defmethod p/update-page :game-over [state]
  (c/update-all (dispatch-components state) state))
