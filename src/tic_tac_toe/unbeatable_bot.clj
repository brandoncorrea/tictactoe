(ns tic-tac-toe.unbeatable-bot
  (:use [tic-tac-toe.game-board]
        [tic-tac-toe.collection-util]
        [tic-tac-toe.minimax]))

;(defn- cell-nil? [[_ value]] (nil? value))
;(defn- cell-equals? [token [_ value]] (= token value))
;(defn has-two-tokens? [token row]
;  (= (dec (count row))
;     (count-by (partial cell-equals? token) row)))

;(defn- best-cell [board token]
;  (->> (series board)
;       (find-first (partial has-two-tokens? token))
;       (find-first cell-nil?)
;       first))

(defn request-move [board player-token opponent-token]
  (optimal-move board player-token opponent-token))
