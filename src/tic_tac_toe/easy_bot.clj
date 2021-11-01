(ns tic-tac-toe.easy-bot
  (:require [tic-tac-toe.player :as player]
            [tic-tac-toe.game-board :as game]))

(deftype EasyBot [token]
  player/Player
  (next-move [_ board]
    (let [cells (game/empty-cells board)]
      (->> cells count rand-int (nth cells))))
  (token [_] token))
