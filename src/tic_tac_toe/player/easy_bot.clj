(ns tic-tac-toe.player.easy-bot
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.game-board :as board]))

(deftype EasyBot [token]
  player/Player
  (next-move [_ board]
    (let [cells (board/empty-cells board)]
      (->> cells count rand-int (nth cells))))
  (token [_] token))
