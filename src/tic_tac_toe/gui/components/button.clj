(ns tic-tac-toe.gui.components.button
  (:require [tic-tac-toe.gui.components.component :as c]
            [quil.core :as q]))

(defn clicked? [x y width height
                 {:keys [up-x up-y down-x down-y]}]
  (let [x2 (+ x width)
        y2 (+ y height)]
    (and up-x up-y down-x down-y
         (<= x up-x x2)
         (<= x down-x x2)
         (<= y up-y y2)
         (<= y down-y y2))))

(deftype button
  [text x y width height onclick text-color back-color]
  c/component

  (draw [_ _]
    (q/fill back-color)
    (q/rect x y width height)
    (q/fill text-color)
    (q/text text (+ x (/ width 2)) (+ y (/ height 2))))

  (update-state [_ state]
    (if (clicked? x y width height (:mouse state))
      (onclick state)
      state)))

(defn ->button
  ([text x y width height update text-color back-color]
   (button. (str text) x y width height update text-color back-color))
  ([text x y width height update]
   (->button text x y width height update 0 255))
  ([text x y width height]
   (->button text x y width height identity))
  ([x y width height]
   (->button "" x y width height)))
