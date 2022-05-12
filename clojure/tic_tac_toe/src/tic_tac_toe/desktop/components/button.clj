(ns tic-tac-toe.desktop.components.button
  (:require [tic-tac-toe.desktop.components.component :as c]
            [quil.core :as q]
            [tic-tac-toe.desktop.colors :as color]))

(defn clicked? [x y width height
                 {:keys [up-x up-y down-x down-y]}]
  (let [x2 (+ x width)
        y2 (+ y height)]
    (and up-x up-y down-x down-y
         (<= x up-x x2)
         (<= x down-x x2)
         (<= y up-y y2)
         (<= y down-y y2))))

(defmethod c/update-state :button
  [{:keys [x y width height onclick]} state]
  (if (clicked? x y width height (:mouse state))
    (onclick state)
    state))

(defmethod c/draw :button
  [{:keys [back-color text-color text x y width height]} _]
  (q/fill back-color)
  (q/rect x y width height)
  (q/fill text-color)
  (q/text (str text) (+ x (/ width 2)) (+ y (/ height 2))))

(defn ->button
  ([] ->button {})
  ([options]
   (merge {:type :button
           :x 0
           :y 0
           :back-color color/white
           :text-color color/black
           :onclick identity
           :text ""}
          options))
  ([text x y width height onclick]
   (->button {:text text :width width :height height :x x :y y :onclick onclick})))
