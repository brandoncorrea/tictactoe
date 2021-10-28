(ns tic-tac-toe.console-io-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.console-io :refer :all]
            [tic-tac-toe.game-board :refer :all]
            [tic-tac-toe.human :refer :all]
            [tic-tac-toe.player :refer :all]
            [tic-tac-toe.user-interface :refer :all]
            [tic-tac-toe.game-board-formatter :refer :all]))

(def header-line "---------------\n")
(def messages ["Header" "Another Header Phrase" ""])
(def io (->ConsoleIO))

(defmacro out-should= [expected actual]
  `(should= ~expected (with-out-str ~actual)))

(describe "Console IO"

  (describe "write-header"
    (it "Displays header with two horizontal lines and newline"
      (loop [[message & rest] messages]
        (when message
          (out-should= (str header-line message "\n" header-line)
                       (write-header message))
          (recur rest)))))

  (describe "show-title"
    (it "Displays the Tic Tac Toe title"
      (out-should= (str header-line "Tic Tac Toe\n" header-line) (show-title io))))

  (describe "show-instructions"
    (it "Writes instructions to the console"
      (out-should= (str "Enter the 0-based index for the row and column\n"
                        "Example: 0 2 for Row 1, Column 3\n")
                   (show-instructions io))))

  (describe "show-results"
    (it "Writes game board and winner to output"
      (let [x-board (->board [\X \X \X])
            o-board (->board [\O \O \O])]
        (out-should= (str "\n" (format-board x-board) "\n" "Game Over! X wins!\n")
                     (show-results io x-board))
        (out-should= (str "\n" (format-board o-board) "\n" "Game Over! O wins!\n")
                     (show-results io o-board))))
    (it "Writes game board and draw result to output"
      (let [board (->board (range))]
        (out-should= (str "\n" (format-board board) "\n" "Game Over! Game was a Draw.\n")
                     (show-results io board)))))

  (describe "request-game-mode"
    (it "Request message is written to output"
      (out-should= (str header-line "Game Mode\n" header-line
                        "1.) Player vs Player\n"
                        "2.) Player vs Computer\n"
                        "Choose Game Mode > ")
                   (with-in-str "1" (request-game-mode io)))))

  (describe "request-move"
    (it "Request message is written to output"
      (out-should= (str "\n[_ _ _]\n[_ _ _]\n[_ _ _]\nX's turn! > ")
                   (with-in-str "1 1" (request-move io (->board []) (->Human \X io)))))
    (it "Results in the cell the user entered"
      ; ignore output
      (with-out-str
        (should= [1 2] (with-in-str "1 2" (request-move io (->board []) (->Human \X io)))))))

  (describe "parse-numbers"
    (it "Empty input results in an empty array"
      (should= [] (parse-numbers "")))
    (it "One number results in an array with that number"
      (should= [1] (parse-numbers "1")))
    (it "Two numbers results in an array with both numbers"
      (should= [1 2] (parse-numbers "1 2")))
    (it "Non-numeric characters are ignored"
      (should= [1 2] (parse-numbers "1a2")))
    (it "Two-digit indices are split as two-digit numbers"
      (should= [10 11] (parse-numbers "10_11")))
    (it "Negative numbers are parsed as negative"
      (should= [-1 2] (parse-numbers "-1 2")))
    (it "Numbers joined by dashes are treated as negative numbers"
      (should= [1 -2 -3] (parse-numbers "1-2-3")))))
