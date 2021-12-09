(ns tic-tac-toe.player.human
  (:require [tic-tac-toe.player.player :as player]))

(defn ->human [token]
  (player/->player :human token))
