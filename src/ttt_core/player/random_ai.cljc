(ns ttt-core.player.random-ai
  (:require [ttt-core.player.player :as player]
            [ttt-core.game-board :as board]))

(defn ->random-ai [token]
  (player/->player :random token))

(defmethod player/next-move :random [_ board]
  (let [cells (board/empty-cells board)]
    (->> cells count rand-int (nth cells))))