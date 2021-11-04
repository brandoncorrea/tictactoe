(ns tic-tac-toe.ui.game-board-formatter
  (:require [clojure.string :as s]
            [tic-tac-toe.game-board :as board]))

(defn- token->str [token]
  (if token (str token) "_"))

(defn format-row [row]
  (str "[" (s/join " " (map token->str row)) "]"))

(defn format-board [board]
  (s/join "\n" (map format-row (board/rows board))))
