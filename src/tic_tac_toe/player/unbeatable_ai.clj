(ns tic-tac-toe.player.unbeatable-ai
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.ai.minimax :as minimax]))

(defn ->unbeatable-ai [token opponent]
  (assoc (player/->player token :unbeatable) :opponent opponent))

(defmethod player/next-move :unbeatable [player board]
  (minimax/optimal-move board (:token player) (:opponent player)))
