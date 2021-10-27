(ns tic-tac-toe.game-board-formatter
  (:use [clojure.string :only [join]]
        [tic-tac-toe.game-board :only [rows]]))

(defn- token->str [token]
  (if token
    (format "%s" token)
    "_"))

(defn format-row [row]
  (str "[" (join " " (map token->str row)) "]"))

(defn format-board [board]
  (join "\n" (map format-row (rows board))))
