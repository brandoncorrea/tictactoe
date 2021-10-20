(ns tic-tac-toe.core
  (:use [tic-tac-toe.game-board]
        [clojure.string :only [join replace trim split]]))

(def new-game (->board (repeat 0)))
(defn format-board [board] (join "\n" (map str board)))

(defn parse-player-choice [choice]
  (map #(Integer. %) (filter not-empty (split choice #""))))

(defn valid-move? [board [row col]]
  (let [size (count board)]
    (and (<= 0 row)
         (<= 0 col)
         (> size row)
         (> size col)
         (= 0 (nth (nth board row) col)))))

(defn get-move-player-1 [board]
  (println (format-board board))
  (println "Make a move, player 1!")
  (loop [move (parse-player-choice (read-line))]
    (if (valid-move? board move)
      (mark-square board move \X)
      (do
        (println "Make a move, player 1!")
        (recur (parse-player-choice (read-line)))))))

(defn play-game []
  (loop [board new-game
         player-1-turn? true]
    (cond
      (game-over? board)
      (do (println "Game Over!")
          (println (format-board board)))
      player-1-turn? (recur (get-move-player-1 board) (not player-1-turn?))
      :else (recur (get-move-player-1 board) (not player-1-turn?)))))

(defn -main [& args]
  (println "Tic-Tac-Toe")
  (println "Player 1: X")
  (println "Player 2: O")
  (play-game))