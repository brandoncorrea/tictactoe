(ns tic-tac-toe.player.human
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.ui.user-interface :as ui]))

(deftype Human [token io]
  player/Player
  (next-move [this board] (ui/request-move io board this))
  (token [_] token))
