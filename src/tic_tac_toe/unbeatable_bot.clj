(ns tic-tac-toe.unbeatable-bot
  (:use [tic-tac-toe.game-board]
        [tic-tac-toe.collection-util]
        [tic-tac-toe.minimax]
        [tic-tac-toe.player]))

(deftype UnbeatableBot [token opponent]
  Player
  (next-move [_ board] (optimal-move board token opponent))
  (token [_] token))
