(ns tic-tac-toe.ui.desktop.components.ttt-board
  (:require [tic-tac-toe.ui.desktop.components.component :as c]
            [tic-tac-toe.ui.desktop.components.button :as b]
            [tic-tac-toe.game-board :as g]
            [tic-tac-toe.ui.desktop.state :as s]))

(defn- ->cell-button [x y width height [[r c] token]]
  (b/->button token
              (+ x (* c width))
              (+ y (* r height))
              width
              height
              (partial s/move-current-player [r c])))

(defn- cell-buttons
  [{width :screen-width height :screen-height :as state}]
  (let [board (s/board state)
        size (g/size board)
        y (/ height 10)
        x (/ width 10)
        cell-height (/ (- height (* y 2)) size)
        cell-width (/ (- width (* x 2)) size)]
    (map (partial ->cell-button x y cell-width cell-height) board)))

(defmethod c/draw :ttt-board [_ state]
  (c/draw-all (cell-buttons state) state))

(defmethod c/update-state :ttt-board [_ state]
  (if (s/game-over? state)
    state
    (c/update-all (cell-buttons state) state)))

(def board {:type :ttt-board})
