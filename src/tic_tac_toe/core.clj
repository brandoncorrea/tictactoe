(ns tic-tac-toe.core
  (:use [tic-tac-toe.game-board]
        [clojure.string :only [join replace trim split]]))

(def new-game (->board (repeat 0)))

(defn format-cell [item]
  (if (= 0 item)
    "_"
    (format "%s" item)))

(defn format-row [row]
  (str "[" (join " " (map format-cell row)) "]"))

(defn format-board [board]
  (join "\n" (map str board)))

(defn parse-input [text]
  (map #(Integer. %) (re-seq #"\d+" text)))

(defn valid-move? [board options]
  (if-let [[row col] options]
    (try (and (= 2 (count options))
              (zero? (nth (nth board row) col)))
         (catch Exception _ false))))

(defn request-move [board token]
  (loop []
    (println (format-board board))
    (println (str token "'s turn!"))
    (let [move (parse-input (read-line))]
      (if (valid-move? board move)
        (mark-square board move token)
        (recur)))))

(defn -main [& args]
  (println "Tic-Tac-Toe")
  (loop [board new-game
         [cur-token next-token] [\X \O]]
    (if (game-over? board)
      (do (println "Game Over!")
          (println (format-board board)))
      (recur (request-move board cur-token) [next-token cur-token]))))