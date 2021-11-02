(ns tic-tac-toe.unbeatable-bot
  (:require [tic-tac-toe.minimax :as minimax]
            [tic-tac-toe.player :as player]))

(deftype UnbeatableBot [token opponent]
  player/Player
  (next-move [_ board] (minimax/optimal-move board token opponent))
  (token [_] token))
