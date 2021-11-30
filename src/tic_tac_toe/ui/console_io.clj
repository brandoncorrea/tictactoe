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

(defn- request-str [message]
  (print (str message " > "))
  (flush)
  (read-line))

(defn- request-until [f pred message]
  (util/any pred (repeatedly #(f message))))

(def request-numbers (comp parse-numbers request-str))
(def request-numbers-until (partial request-until request-numbers))
(def request-str-until (partial request-until request-str))
(defn- request-int-until [pred message]
  (util/any pred (map first (take-while #(= 1 (count %)) (repeatedly #(request-numbers message))))))

(defn- request-int-option [options message]
  (get options (request-int-until (set (keys options)) message)))

(defn- request-str-option [options message]
  (get options (request-str-until (set (keys options)) message)))

(defn ->consoleIO [] {:type :console})

(defmethod ui/show-title :console [_]
  (write-header "Tic Tac Toe"))

(defmethod ui/show-instructions :console [_]
  (println "Enter the 0-based index for the row and column")
  (println "Example: 0 2 for Row 1, Column 3"))

(defmethod ui/show-results :console [_ board]
  (show-board board)
  (let [results (board/game-results board)]
    (if (:draw results)
      (println "Game Over! Game was a Draw.")
      (println (str "Game Over! " (:winner results) " wins!")))))

(defmethod ui/request-game-mode :console [_]
  (write-header "Game Mode")
  (println "1.) Player vs Player")
  (println "2.) Player vs Computer")
  (request-int-option {1 :player-vs-player 2 :player-vs-computer}
                      "Choose Game Mode"))

(defmethod ui/request-board-size :console [_]
  (write-header "Board Size")
  (println "1.) 3x3")
  (println "2.) 4x4")
  (request-int-option {1 3 2 4} "Choose Board Size"))

(defmethod ui/request-board-dimensions :console [_ size]
  (if (not= 3 size)
    2
    (do
      (write-header "Board Dimensions")
      (println "1.) 2D")
      (println "2.) 3D")
      (request-int-option {1 2 2 3} "Choose Board Dimensions"))))

(defmethod ui/request-difficulty :console [_]
  (write-header "Difficulty")
  (println "1. Easy")
  (println "2. Medium")
  (println "3. Hard")
  (request-int-option {1 :easy 2 :medium 3 :hard} "Choose Difficulty"))

(defmethod ui/request-move :console [_ board player]
  (show-board board)
  (request-numbers-until (partial board/valid-move? board) (str (:token player) "'s turn!")))

(defmethod ui/resume-game? :console [_]
  (write-header "Resume?")
  (request-str-option {"Y" true "y" true "N" false "n" false} "Resume last game? (Y/N)"))