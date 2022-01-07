(ns ttt-cljs.app
  (:require [ttt-cljs.components.board :as c]
            [ttt-cljs.components.menu :as m]
            [ttt-cljs.state :as s]
            [ttt-cljs.components.status :as status]))

(defn app []
   [:div
    [:h1 {:style {:text-align "center"}} "Tic Tac Toe"]
    (status/status (:game @s/state))
    (c/board (s/board))
    (m/menu)])
