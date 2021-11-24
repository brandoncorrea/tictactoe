(ns tic-tac-toe.player.random-ai
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.game-board :as board]))

(defn ->random-ai [token]
  (player/->player token :random))

(defmethod player/next-move :random [_ board]
  (let [cells (board/empty-cells board)]
    (->> cells count rand-int (nth cells))))