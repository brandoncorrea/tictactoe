(ns tic-tac-toe.player.medium-bot
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.ai.minimax :as minimax]))

(defn ->medium-bot [token opponent]
  (assoc (player/->player token :medium) :opponent opponent))

(defmethod player/next-move :medium [player board]
  (minimax/optimal-move board (:token player) (:opponent player) 4))