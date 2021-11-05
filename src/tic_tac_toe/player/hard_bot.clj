(ns tic-tac-toe.player.hard-bot
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.minimax :as minimax]))

(defn ->hard-bot [token opponent]
  (assoc (player/->player token :hard) :opponent opponent))

(defmethod player/next-move :hard [player board]
  (minimax/optimal-move board (:token player) (:opponent player)))
