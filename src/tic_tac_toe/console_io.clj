(ns tic-tac-toe.console-io
  (:require [tic-tac-toe.game-board-formatter :as formatter]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.game-board :as board]
            [tic-tac-toe.player :as player]))

(def ^:private horizontal-line (apply str (repeat 15 "-")))
(defn- show-board [board]
  (println)
  (println (formatter/format-board board)))

(defn parse-numbers [text]
  (map #(Integer. %) (re-seq #"-?\d+" text)))

(defn write-header [message]
  (println horizontal-line)
  (println message)
  (println horizontal-line))

(defn- request-numbers [message]
  (print (str message " > "))
  (flush)
  (parse-numbers (read-line)))

(defn- request-numbers-until [pred message]
  (first (filter pred (repeatedly #(request-numbers message)))))

(defn- request-int-until [pred message]
  (first (filter pred (map first (take-while #(= 1 (count %)) (repeatedly #(request-numbers message)))))))

(deftype ConsoleIO [] ui/UserInterface
  (show-title [_]
    (write-header "Tic Tac Toe"))

  (show-instructions [_]
    (println "Enter the 0-based index for the row and column")
    (println "Example: 0 2 for Row 1, Column 3"))

  (show-results [_ board]
    (show-board board)
    (let [results (board/game-results board)]
      (if (:draw results)
        (println "Game Over! Game was a Draw.")
        (println (str "Game Over! " (:winner results) " wins!")))))

  (request-game-mode [_]
    (write-header "Game Mode")
    (println "1.) Player vs Player")
    (println "2.) Player vs Computer")
    (if (= 1 (request-int-until #{1 2} "Choose Game Mode"))
      :player-vs-player
      :player-vs-computer))

  (request-difficulty [_]
    (write-header "Difficulty")
    (println "1. Easy")
    (println "2. Hard")
    (if (= 1 (request-int-until #{1 2} "Choose Difficulty"))
      :easy
      :hard))

  (request-move [_ board player]
    (show-board board)
    (request-numbers-until (partial board/valid-move? board) (str (player/token player) "'s turn!"))))
