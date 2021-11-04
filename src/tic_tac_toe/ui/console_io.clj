(ns tic-tac-toe.ui.console-io
  (:require [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.game-board :as board]
            [tic-tac-toe.ui.game-board-formatter :as formatter]
            [tic-tac-toe.util.collections :as util]))

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
  (util/find-first pred (repeatedly #(request-numbers message))))

(defn- request-int-until [pred message]
  (util/find-first pred (map first (take-while #(= 1 (count %)) (repeatedly #(request-numbers message))))))

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
    ({1 :player-vs-player 2 :player-vs-computer}
     (request-int-until #{1 2} "Choose Game Mode")))

  (request-difficulty [_]
    (write-header "Difficulty")
    (println "1. Easy")
    (println "2. Medium")
    (println "3. Hard")
    ({1 :easy 2 :medium 3 :hard}
     (request-int-until #{1 2 3} "Choose Difficulty")))

  (request-move [_ board player]
    (show-board board)
    (request-numbers-until (partial board/valid-move? board) (str (:token player) "'s turn!"))))
