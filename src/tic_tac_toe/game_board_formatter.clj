(ns tic-tac-toe.game-board-formatter
  (:use [clojure.string :only [join]]))

(defn- token->str [token]
  (if (= 0 token)
    "_"
    (format "%s" token)))

(defn format-row [row]
  (str "[" (join " " (map token->str row)) "]"))

(defn format-board [board]
  (join "\n" (map format-row board)))