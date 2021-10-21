(ns tic-tac-toe.core
  (:use [tic-tac-toe.game-board]
        [clojure.string :only [join replace trim split]]
        [tic-tac-toe.game-board-formatter]
        [tic-tac-toe.console-io]))

(defn parse-input [text]
  (map #(Integer. %) (re-seq #"\d+" text)))

(defn valid-move? [board options]
  (if-let [[row col] options]
    (try (and (= 2 (count options))
              (zero? (nth (nth board row) col)))
         (catch Exception _ false))))

(defn update-board [board token]
  (loop []
    (let [move (parse-input (request-move token board))]
      (if (valid-move? board move)
        (mark-square board move token)
        (recur)))))

(defn -main [& _]
  (write-header "Tic-Tac-Toe")
  (write-message "Enter the 0-based index for the row and column (ex: 0 2)")
  (loop [board (->board (repeat 0))
         [cur-token next-token] [\X \O]]
    (if (game-over? board)
      (write-message "Game Over!" board)
      (recur (update-board board cur-token) [next-token cur-token]))))