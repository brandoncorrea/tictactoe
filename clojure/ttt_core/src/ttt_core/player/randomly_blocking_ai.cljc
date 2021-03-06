(ns ttt-core.player.randomly-blocking-ai
  (:require [ttt-core.player.player :as player]
            [ttt-core.game-board :as board]))

(defn ->randomly-blocking-ai [token opponent]
  (player/->player :randomly-blocking token opponent))

(defn- block? [percentage]
  (< (rand-int 100) percentage))

(defn- random-cell [board]
  (let [cells (board/empty-cells board)]
    (nth cells (rand-int (count cells)))))

(defmethod player/next-move :randomly-blocking
  [player board]
  (let [paths (board/series board)]
    (or (board/winning-cell (:token player) paths)
        (and (block? 50)
             (board/winning-cell (:opponent player) paths))
        (random-cell board))))
