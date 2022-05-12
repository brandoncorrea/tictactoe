(ns tic-tac-toe.desktop.pages.load-game
  (:require [tic-tac-toe.desktop.pages.page :as p]
            [tic-tac-toe.desktop.components.header :as h]
            [tic-tac-toe.desktop.components.component :as c]
            [tic-tac-toe.desktop.components.button :as b]
            [tic-tac-toe.desktop.state :as s]))

(def components
  [(h/->header "Resume Last Game?")
   (b/->button "Resume" 125 50 100 30 #(s/navigate % :play))
   (b/->button "New Game" 275 50 100 30 #(s/navigate % :new-game))])

(defmethod p/render-page :load-game [state]
  (c/draw-all components state))

(defmethod p/update-page :load-game [state]
  (c/update-all components state))