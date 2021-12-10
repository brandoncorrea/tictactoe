(ns tic-tac-toe.gui.components.toggle-button
  (:require [tic-tac-toe.gui.components.component :as c]
            [tic-tac-toe.gui.components.button :as b]))

(def green (Integer. 0xFF00FF00))

(deftype toggle-button [text x y width height update toggled? text-color back-color]
  c/component
  (draw [_ state]
    (let [back-color (if (toggled? state) green back-color)]
      (c/draw (b/->button text x y width height update text-color back-color) state)))
  (update-state [_ state]
    (c/update-state (b/->button text x y width height update text-color green) state)))

(defn ->toggle-button
  ([text x y width height update toggled? text-color back-color]
    (toggle-button. text x y width height update toggled? text-color back-color))
  ([text x y width height update toggled?]
    (->toggle-button text x y width height update toggled? 0 255)))
