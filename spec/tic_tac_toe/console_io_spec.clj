(ns tic-tac-toe.console-io-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.console-io :refer :all]
            [tic-tac-toe.game-board :refer :all]
            [tic-tac-toe.game-board-formatter :refer :all]))

(def header-line "---------------\n")
(def messages ["Header" "Another Header Phrase" ""])

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

  (describe "write-message"
    (it "Writes plaintext message out to console with a newline"
      (loop [[message & rest] messages]
        (when message
          (out-should= (str message "\n") (write-message message)))))
    (it "Writes message out beneath game board"
      (let [board (->board (repeat nil))]
        (out-should= (str "\n" (format-board board) "\nSome Message\n")
                     (write-message "Some Message" board)))))

  (describe "show-results"
    (it "Writes game board and winner to output"
      (let [board (->board (repeat nil))]
        (out-should= (str "\n" (format-board board) "\n" "Game Over! X wins!\n")
                     (show-results {:game-over true :winner \X :draw false} board))
        (out-should= (str "\n" (format-board board) "\n" "Game Over! O wins!\n")
                     (show-results {:game-over true :winner \O :draw false} board))))
    (it "Writes game board and draw result to output"
      (let [board (->board (repeat nil))]
        (out-should= (str "\n" (format-board board) "\n" "Game Over! Game was a Draw.\n")
                     (show-results {:game-over true :winner nil :draw true} board)))))

  (describe "request-move"
    (it "Writes board and token to output"
      (let [board (->board (repeat nil))]
        (out-should= (str "\n" (format-board board) "\n" "X's move! > ")
                     (with-in-str "" (request-move \X board)))
        (out-should= (str "\n" (format-board board) "\n" "O's move! > ")
                     (with-in-str "" (request-move \O board)))))))

