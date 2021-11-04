(ns tic-tac-toe.player.player
  (:require [tic-tac-toe.minimax :as minimax]
            [tic-tac-toe.game-board :as board]
            [tic-tac-toe.ui.user-interface :as ui]))

(defn- ->player [token type]
  {:token token :type type})

(defn ->human [token ui]
  (assoc (->player token :human) :ui ui))

(defn ->easy-bot [token]
  (->player token :easy))

(defn ->medium-bot [token opponent]
  (assoc (->player token :medium) :opponent opponent))

(defn ->hard-bot [token opponent]
  (assoc (->player token :hard) :opponent opponent))

(defmulti next-move (fn [player _] (:type player)))

(defmethod next-move :human [player board]
  (ui/request-move (:ui player) board player))

(defmethod next-move :easy [_ board]
  (let [cells (board/empty-cells board)]
    (->> cells count rand-int (nth cells))))

(defmethod next-move :medium [player board]
  (minimax/optimal-move board (:token player) (:opponent player) 4))

(defmethod next-move :hard [player board]
  (minimax/optimal-move board (:token player) (:opponent player)))