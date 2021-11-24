(ns tic-tac-toe.player.randomly-blocking-ai
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.game-board :as board]))

(defn ->randomly-blocking-ai [token opponent]
  (player/->player :randomly-blocking token opponent))

(defn- block? [percentage]
  (< (rand-int 100) percentage))

(defn- random-cell [board]
  (let [cells (board/empty-cells board)]
    (nth cells (rand-int (count cells)))))

(defn winning-path? [token path]
  (let [values (vals path)
        unchecked-cells (filter (partial not= token) values)]
    (and (= 1 (count unchecked-cells))
         (nil? (first unchecked-cells)))))

(defn- winning-paths [token paths]
  (filter (partial winning-path? token) paths))

(defn blocking-cell [player board]
  (->> (board/series board)
       (winning-paths (:opponent player))
       first
       (filter board/cell-empty?)
       ffirst))

(defn winning-cell [player board]
  (let [paths (winning-paths (:token player) (board/series board))]
    (if-not (empty? paths)
      (ffirst (filter board/cell-empty? (first paths))))))

(defmethod player/next-move :randomly-blocking
  [player board]
  (or (winning-cell player board)
      (and (block? 50)
           (blocking-cell player board))
      (random-cell board)))
