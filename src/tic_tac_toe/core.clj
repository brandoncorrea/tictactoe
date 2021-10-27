(ns tic-tac-toe.core
  (:use [tic-tac-toe.game-board]
        [clojure.string :only [join replace trim split]]
        [tic-tac-toe.game-board]
        [tic-tac-toe.console-io]))

(defn parse-input [text]
  (map #(Integer. %) (re-seq #"-?\d+" text)))

(defn update-board [board token]
  (loop []
    (let [move (parse-input (request-move token board))]
      (if (valid-move? board move)
        (mark-square board move token)
        (recur)))))

(defn -main [& _]
  (write-header "Tic-Tac-Toe")
  (write-message "Enter the 0-based index for the row and column")
  (write-message "Example: 0 2 for Row 1, Column 3")
  (loop [board (->board (repeat nil))
         [cur-token next-token] [\X \O]]
    (let [results (game-results board)]
      (if (:game-over results)
        (show-results results board)
        (recur (update-board board cur-token) [next-token cur-token])))))
