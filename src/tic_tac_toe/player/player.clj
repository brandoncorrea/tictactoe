(ns tic-tac-toe.player.player)

(defprotocol Player
  (next-move [this board])
  (token [this]))
