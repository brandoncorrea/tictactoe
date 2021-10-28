(ns tic-tac-toe.player)

(defprotocol Player
  (next-move [this board])
  (token [this]))
