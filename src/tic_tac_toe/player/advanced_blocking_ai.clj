(ns tic-tac-toe.player.advanced-blocking-ai
  (:require [tic-tac-toe.player.player :as player]
            [tic-tac-toe.game-board :as board]
            [tic-tac-toe.util.collections :refer [any]]))

(defn ->advanced-blocking-ai [token opponent]
  (player/->player :advanced-blocking token opponent))

(defn greatest [token path-1 path-2]
  (let [vals-1 (frequencies (vals path-1))
        vals-2 (frequencies (vals path-2))]
    (cond
      (nil? (get vals-1 nil)) path-2
      (nil? (get vals-2 nil)) path-1
      (= 3 (count vals-1)) path-2
      (= 3 (count vals-2)) path-1
      (and (= 1 (get vals-1 nil))
           (get vals-1 token)) path-1
      (and (= 1 (get vals-2 nil))
           (get vals-2 token)) path-2
      (= 1 (get vals-1 nil)) path-1
      (= 1 (get vals-2 nil)) path-2
      (> (get vals-1 token 0) (get vals-2 token 0)) path-1
      (< (get vals-1 token 0) (get vals-2 token 0)) path-2
      (< (get vals-1 nil 0) (get vals-2 nil 0)) path-2
      :else path-1)))

(defmethod player/next-move :advanced-blocking
  [player board]
  (let [paths (board/series board)]
    (or (board/winning-cell (:token player) paths)
        (board/winning-cell (:opponent player) paths)
        (board/any-empty-cell (reduce (partial greatest (:token player)) paths)))))
