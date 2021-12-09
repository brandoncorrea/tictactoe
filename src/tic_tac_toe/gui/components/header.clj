(ns tic-tac-toe.gui.components.header
  (:require [quil.core :as q]
            [tic-tac-toe.gui.components.component :as c]))

(deftype header [text] c/component
  (draw [_ {width :screen-width height :screen-height}]
    (q/text text (/ width 2) (/ height 25)))
  (update-state [_ state] state))
