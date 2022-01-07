(ns ttt-cljs.app-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [ttt-cljs.app :as a]
            [ttt-cljs.components.board :as c]
            [ttt-cljs.components.menu :as m]
            [ttt-cljs.state :as s]
            [ttt-cljs.components.results :as r]))

(describe "app"
  (it "renders title, game board, and menu options"
    (should= [:div [:h1 {:style {:text-align "center"}} "Tic Tac Toe"]
              (r/results (:game @s/state))
              (c/board (s/board))
              (m/menu)]
             (a/app))))
