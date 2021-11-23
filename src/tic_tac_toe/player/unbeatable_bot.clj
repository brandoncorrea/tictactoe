(ns tic-tac-toe.player.unbeatable-bot
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.ai.minimax :as minimax]))

(defn ->unbeatable-bot [token opponent]
  (assoc (player/->player token :unbeatable) :opponent opponent))

(defmethod player/next-move :unbeatable [player board]
  (minimax/optimal-move board (:token player) (:opponent player)))
