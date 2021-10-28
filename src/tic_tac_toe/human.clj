(ns tic-tac-toe.human
  (:use [tic-tac-toe.player]
        [tic-tac-toe.user-interface]))

(deftype Human [token io]
  Player
  (next-move [this board] (request-move io board this))
  (token [_] token))
