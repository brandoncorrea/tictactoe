(ns tic-tac-toe.medium-bot
  (:require [tic-tac-toe.player :as player]
            [tic-tac-toe.minimax :as minimax]))

(deftype MediumBot [token opponent]
  player/Player
  (next-move [_ board] (minimax/optimal-move board token opponent 4))
  (token [_] token))