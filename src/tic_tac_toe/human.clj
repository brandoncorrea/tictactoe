(ns tic-tac-toe.human
  (:require [tic-tac-toe.player :as player]
            [tic-tac-toe.user-interface :as ui]))

(deftype Human [token io]
  player/Player
  (next-move [this board] (ui/request-move io board this))
  (token [_] token))
