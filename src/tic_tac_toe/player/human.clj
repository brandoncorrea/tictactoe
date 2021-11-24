(ns tic-tac-toe.player.human
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.ui.user-interface :as ui]))

(defn ->human [token ui]
  (assoc (player/->player :human token) :ui ui))

(defmethod player/next-move :human [player board]
  (ui/request-move (:ui player) board player))
