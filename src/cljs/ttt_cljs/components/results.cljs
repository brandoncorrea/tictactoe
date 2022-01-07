(ns ttt-cljs.components.results
  (:require [ttt-core.game :as g]))

(defn results [game]
  (vec (concat
         [:h3 {:style {:text-align "center"}}]
         (if (g/game-over? game)
           [(if (g/draw? game)
              "Game Over – Draw!"
              (str "Game Over – " (g/winner game) " Wins!"))]
           [(str (get-in game [:next-player :token]) "'s turn")]))))
