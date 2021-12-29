(ns tic-tac-toe.gui.components.header
  (:require [quil.core :as q]
            [tic-tac-toe.gui.components.component :as c]))

(defmethod c/draw :header
  [{:keys [text]} {:keys [screen-width screen-height]}]
  (q/text text (/ screen-width 2) (/ screen-height 25)))

(defmethod c/update-state :header [_ state]
  state)

(defn ->header [text]
  {:type :header :text text})