(ns tic-tac-toe.console-io
  (:use [tic-tac-toe.game-board-formatter]))

(def ^:private horizontal-line (apply str (repeat 15 "-")))

(defn write-header [message]
  (println horizontal-line)
  (println message)
  (println horizontal-line))

(defn write-message
  ([message]
   (println)
   (println message))
  ([message board]
   (println)
   (println (format-board board))
   (println message)))

(defn request-move [token board]
  (println (format-board board))
  (print (str token "'s move! > "))
  (flush)
  (read-line))