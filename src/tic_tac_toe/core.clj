(ns tic-tac-toe.core
  (:use [tic-tac-toe.game-board]
        [clojure.string :only [join replace trim split]]
        [tic-tac-toe.game-board]
        [tic-tac-toe.console-io]
        [tic-tac-toe.collection-util]))

(defn- next-move [board token]
  (find-first (partial valid-move? board)
              (repeatedly #(request-move token board))))

(defn- update-board [board token]
  (mark-square board (next-move board token) token))

(defn -main [& _]
  (write-header "Tic-Tac-Toe")
  (write-message "Enter the 0-based index for the row and column")
  (write-message "Example: 0 2 for Row 1, Column 3")
  (loop [board (->board (repeat nil))
         [token next-token] [\X \O]]
    (let [results (game-results board)]
      (if (:game-over results)
        (show-results results board)
        (recur (update-board board token) [next-token token])))))
