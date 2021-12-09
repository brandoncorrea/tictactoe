(ns tic-tac-toe.gui.components.mouse
  (:require [quil.core :as q]))

(defn ->mouse []
  (let [x (q/mouse-x)
        y (q/mouse-y)]
    (if (q/mouse-pressed?)
      {:up-x nil
       :up-y nil
       :down-x x
       :down-y y}
      {:up-x nil :up-y nil :down-x nil :down-y nil})))

(defn update-mouse
  [{:keys [up-x up-y down-x down-y] :as mouse}]
  (let [x (q/mouse-x)
        y (q/mouse-y)]
    (cond
      (q/mouse-pressed?)
        (if (and down-x down-y)
          {:up-x nil :up-y nil :down-x down-x :down-y down-y}
          {:up-x nil :up-y nil :down-x x :down-y y})
      (and up-x up-y)
        {:up-x nil :up-y nil :down-x nil :down-y nil}
      :else (assoc mouse :up-x x :up-y y))))
