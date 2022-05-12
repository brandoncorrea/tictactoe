(ns ttt-core.player.medium-bot
  (:require [ttt-core.player.player :as player]
            [ttt-core.ai.minimax :as minimax]))

(defn ->medium-bot [token opponent]
  (player/->player :medium token opponent))

(defmethod player/next-move :medium [player board]
  (minimax/optimal-move board (:token player) (:opponent player) 4))