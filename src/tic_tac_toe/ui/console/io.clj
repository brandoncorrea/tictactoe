(ns tic-tac-toe.ui.console.io
  (:require [ttt-core.game-board :as board]
            [tic-tac-toe.ui.console.formatter :as f]))

(defn string->int [s] (Integer. s))
(defn any [pred coll] (first (filter pred coll)))
(def ^:private horizontal-line (apply str (repeat 15 "-")))
(defn- show-board [board]
  (println)
  (println (f/format-board board)))

(defn parse-numbers [text]
  (map string->int (re-seq #"-?\d+" text)))

(defn write-header [message]
  (println horizontal-line)
  (println message)
  (println horizontal-line))

(defn- request-str [message]
  (print (str message " > "))
  (flush)
  (read-line))

(defn- request-until [f pred message]
  (any pred (repeatedly #(f message))))

(def request-numbers (comp parse-numbers request-str))
(def request-numbers-until (partial request-until request-numbers))
(def request-str-until (partial request-until request-str))
(defn- request-int-until [pred message]
  (any pred (map first (take-while #(= 1 (count %)) (repeatedly #(request-numbers message))))))

(defn- request-int-option [options message]
  (get options (request-int-until (set (keys options)) message)))

(defn- request-str-option [options message]
  (get options (request-str-until (set (keys options)) message)))

(defn show-title []
  (write-header "Tic Tac Toe"))

(defn show-instructions []
  (println "Enter the 0-based index for the row and column")
  (println "Example: 0 2 for Row 1, Column 3"))

(defn show-results [board]
  (show-board board)
  (let [results (board/game-results board)]
    (if (:draw results)
      (println "Game Over! Game was a Draw.")
      (println (str "Game Over! " (:winner results) " wins!")))))

(defn request-game-mode []
  (write-header "Game Mode")
  (println "1.) Player vs Player")
  (println "2.) Player vs Computer")
  (request-int-option {1 :player-vs-player 2 :player-vs-computer}
                      "Choose Game Mode"))

(defn request-board-size []
  (write-header "Board Size")
  (println "1.) 3x3")
  (println "2.) 4x4")
  (request-int-option {1 3 2 4} "Choose Board Size"))

(defn request-board-dimensions [size]
  (if (not= 3 size)
    2
    (do
      (write-header "Board Dimensions")
      (println "1.) 2D")
      (println "2.) 3D")
      (request-int-option {1 2 2 3} "Choose Board Dimensions"))))

(defn request-difficulty []
  (write-header "Difficulty")
  (println "1. Easy")
  (println "2. Medium")
  (println "3. Hard")
  (request-int-option {1 :easy 2 :medium 3 :hard} "Choose Difficulty"))

(defn request-move [board player]
  (show-board board)
  (request-numbers-until (partial board/valid-move? board) (str (:token player) "'s turn!")))

(defn resume-game? []
  (write-header "Resume?")
  (request-str-option {"Y" true "y" true "N" false "n" false} "Resume last game? (Y/N)"))