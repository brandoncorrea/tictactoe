(ns ttt-core.player.human
  (:require [ttt-core.player.player :as player]))

(defn ->human [token]
  (player/->player :human token))
