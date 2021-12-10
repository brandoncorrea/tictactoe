(ns tic-tac-toe.gui.components.toggle-button
  (:require [tic-tac-toe.gui.components.component :as c]
            [tic-tac-toe.gui.components.button :as b]
            [tic-tac-toe.gui.colors :as color]))

(deftype toggle-button [text x y width height update toggled? text-color back-color]
  c/component
  (draw [_ state]
    (let [[back-color text-color] (if (toggled? state) [color/green back-color] [back-color text-color])]
      (c/draw (b/->button text x y width height update text-color back-color) state)))
  (update-state [_ state]
    (c/update-state (b/->button text x y width height update text-color color/green) state)))

(defn ->toggle-button
  ([text x y width height update toggled? text-color back-color]
    (toggle-button. text x y width height update toggled? text-color back-color))
  ([text x y width height update toggled?]
    (->toggle-button text x y width height update toggled? color/black color/white)))
