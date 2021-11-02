(ns tic-tac-toe.game-board-formatter
  (:require [clojure.string :as s]
            [tic-tac-toe.game-board :as board]))

(defn- token->str [token]
  (if token
    (format "%s" token)
    "_"))

(defn format-row [row]
  (str "[" (s/join " " (map token->str row)) "]"))

(defn format-board [board]
  (s/join "\n" (map format-row (board/rows board))))
