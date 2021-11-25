(ns tic-tac-toe.ui.console-io
  (:require [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.game-board :as board]
            [tic-tac-toe.ui.game-board-formatter :as formatter]
            [tic-tac-toe.util.collections :as util]
            [tic-tac-toe.util.int-math :as math]))

(def ^:private horizontal-line (apply str (repeat 15 "-")))
(defn- show-board [board]
  (println)
  (println (formatter/format-board board)))

(defn parse-numbers [text]
  (map math/string->int (re-seq #"-?\d+" text)))

(defn write-header [message]
  (println horizontal-line)
  (println message)
  (println horizontal-line))

(defn- request-numbers [message]
  (print (str message " > "))
  (flush)
  (parse-numbers (read-line)))

(defn- request-numbers-until [pred message]
  (util/any pred (repeatedly #(request-numbers message))))

(defn- request-int-until [pred message]
  (util/any pred (map first (take-while #(= 1 (count %)) (repeatedly #(request-numbers message))))))

(defn- request-int-option [options message]
  (get options (request-int-until (set (keys options)) message)))

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
    (request-int-option {1 :player-vs-player 2 :player-vs-computer}
                        "Choose Game Mode"))

  (request-board-size [_]
    (write-header "Board Size")
    (println "1.) 3x3")
    (println "2.) 4x4")
    (request-int-option {1 3 2 4} "Choose Board Size"))

  (request-board-dimensions [_ size]
    (if (not= 3 size)
      2
      (do
        (write-header "Board Dimensions")
        (println "1.) 2D")
        (println "2.) 3D")
        (request-int-option {1 2 2 3} "Choose Board Dimensions"))))

  (request-difficulty [_]
    (write-header "Difficulty")
    (println "1. Easy")
    (println "2. Medium")
    (println "3. Hard")
    (request-int-option {1 :easy 2 :medium 3 :hard} "Choose Difficulty"))

  (request-move [_ board player]
    (show-board board)
    (request-numbers-until (partial board/valid-move? board) (str (:token player) "'s turn!"))))
