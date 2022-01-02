(ns tic-tac-toe.player.medium-bot
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.ai.minimax :as minimax]))

(defn ->medium-bot [token opponent]
  (player/->player :medium token opponent))

(defmethod player/next-move :medium [player board]
  (minimax/optimal-move board (:token player) (:opponent player) 4))