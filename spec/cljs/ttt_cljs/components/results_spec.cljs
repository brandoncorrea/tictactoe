(ns ttt-cljs.components.results-spec
  (:require-macros [speclj.core :refer [describe it should should=]])
  (:require [speclj.core]
            [ttt-cljs.components.results :as r]
            [ttt-core.game :as g]
            [ttt-core.game-board :as b]))

(describe "Results message"
  (it "is a vector"
    (should (vector? (r/results (g/create-game)))))
  (for [[p1 p2] [[\X \O] [\O \X]]]
    (it (str "displays player " p1 "'s turn when game is not over")
      (should= [:h3 {:style {:text-align "center"}} (str p1 "'s turn")] (r/results (g/create-game {:token-1 p1 :token-2 p2})))))
  (it "displays draw when game is a draw"
    (should= [:h3 {:style {:text-align "center"}} "Game Over – Draw!"]
             (r/results (assoc (g/create-game) :board (b/->board (range))))))
  (for [token [\X \O]]
    (it (str "displays winning token when " token " wins")
      (should= [:h3 {:style {:text-align "center"}} (str "Game Over – " token " Wins!")]
               (r/results (assoc (g/create-game) :board (b/->board [token token token])))))))