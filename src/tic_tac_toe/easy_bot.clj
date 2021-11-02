(ns tic-tac-toe.easy-bot
  (:require [tic-tac-toe.player :as player]
            [tic-tac-toe.game-board :as board]))

(deftype EasyBot [token]
  player/Player
  (next-move [_ board]
    (let [cells (board/empty-cells board)]
      (->> cells count rand-int (nth cells))))
  (token [_] token))
