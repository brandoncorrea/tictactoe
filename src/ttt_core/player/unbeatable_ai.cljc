(ns ttt-core.player.unbeatable-ai
  (:require [ttt-core.player.player :as player]
            [ttt-core.ai.minimax :as minimax]))

(defn ->unbeatable-ai [token opponent]
  (player/->player :unbeatable token opponent))

(defmethod player/next-move :unbeatable [player board]
  (minimax/optimal-move board (:token player) (:opponent player)))
