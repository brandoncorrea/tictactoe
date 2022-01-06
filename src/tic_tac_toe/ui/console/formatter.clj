(ns tic-tac-toe.ui.console.formatter
  (:require [clojure.string :as s]
            [ttt-core.game-board :as board]))

(defn- token->str [token]
  (if token (str token) "_"))

(defn format-row [row]
  (str "[" (s/join " " (map token->str row)) "]"))

(defmulti format-board board/dimensions)

(defmethod format-board :default [board]
  (s/join "\n" (map format-row (board/rows board))))

(defmethod format-board 3 [board]
  (let [size (board/size board)]
    (as-> (board/rows board) $
          (map format-row $)
          (partition size $)
          (map (fn [n] (map #(nth % n) $)) (range size))
          (map #(s/join "\t" %) $)
          (s/join "\n" $))))
