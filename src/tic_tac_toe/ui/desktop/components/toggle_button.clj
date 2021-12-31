(ns tic-tac-toe.ui.desktop.components.toggle-button
  (:require [tic-tac-toe.ui.desktop.components.component :as c]
            [tic-tac-toe.ui.desktop.colors :as color]))

(defn ->toggle-button
  ([] ->toggle-button {})
  ([options]
   (merge {:type :toggle-button
           :x 0
           :y 0
           :back-color color/white
           :text-color color/black
           :toggled? (fn [_] false)
           :onclick identity
           :text ""}
          options))
  ([text x y width height onclick toggled?]
   (->toggle-button {:text text :x x :y y :width width :height height :onclick onclick :toggled? toggled?})))

(defmethod c/draw :toggle-button
  [{:keys [back-color text-color toggled?] :as button} state]
  (let [[back-color text-color] (if (toggled? state) [color/green back-color] [back-color text-color])]
    (c/draw (assoc button :back-color back-color :text-color text-color :type :button) state)))

(defmethod c/update-state :toggle-button
  [button state]
  (c/update-state (assoc button :type :button) state))
