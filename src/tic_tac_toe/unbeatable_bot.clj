(ns tic-tac-toe.unbeatable-bot
  (:use [tic-tac-toe.game-board]))

(defn- cell-nil? [[_ value]] (nil? value))
(defn- cell-equals? [token [_ value]] (= token value))
(defn has-two-tokens? [token row]
  (= 2 (count (filter (partial cell-equals? token) row))))

(defn- best-cell [board token]
  (->> (series board)
       (filter (partial has-two-tokens? token))
       first
       (filter cell-nil?)
       ffirst))

(defn request-move [token board]
  (or (best-cell board token)
      [0 0]))
