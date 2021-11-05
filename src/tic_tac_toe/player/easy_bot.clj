(ns tic-tac-toe.player.easy-bot
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.game-board :as board]))

(defn ->easy-bot [token]
  (player/->player token :easy))

(defmethod player/next-move :easy [_ board]
  (let [cells (board/empty-cells board)]
    (->> cells count rand-int (nth cells))))