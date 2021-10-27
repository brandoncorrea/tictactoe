(ns tic-tac-toe.console-io
  (:use [tic-tac-toe.game-board-formatter :only [format-board]]))

(def ^:private horizontal-line (apply str (repeat 15 "-")))
(defn- print-board [board]
  (println)
  (println (format-board board)))

(defn parse-input [text]
  (map #(Integer. %) (re-seq #"-?\d+" text)))

(defn write-header [message]
  (println horizontal-line)
  (println message)
  (println horizontal-line))

(defn write-message
  ([message]
   (println message))
  ([message board]
   (print-board board)
   (println message)))

(defn show-results [results board]
  (if (:draw results)
    (write-message "Game Over! Game was a Draw." board)
    (write-message (str "Game Over! " (:winner results) " wins!") board)))

(defn request-move [token board]
  (print-board board)
  (print (str token "'s move! > "))
  (flush)
  (parse-input (read-line)))