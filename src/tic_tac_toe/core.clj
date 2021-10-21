(ns tic-tac-toe.core
  (:use [tic-tac-toe.game-board]
        [clojure.string :only [join replace trim split]]
        [tic-tac-toe.game-board-formatter]
        [tic-tac-toe.console-writer]))

(defn parse-input [text]
  (map #(Integer. %) (re-seq #"\d+" text)))

(defn valid-move? [board options]
  (if-let [[row col] options]
    (try (and (= 2 (count options))
              (zero? (nth (nth board row) col)))
         (catch Exception _ false))))

(defn request-move [message board]
  (write-message message board)
  (parse-input (read-line)))

(defn update-board [board token]
  (loop []
    (let [move (request-move (str token "'s turn!") board)]
      (if (valid-move? board move)
        (mark-square board move token)
        (recur)))))

(defn -main [& _]
  (write-header "Tic-Tac-Toe")
  (loop [board (->board (repeat 0))
         [cur-token next-token] [\X \O]]
    (if (game-over? board)
      (write-message "Game Over!" board)
      (recur (update-board board cur-token) [next-token cur-token]))))