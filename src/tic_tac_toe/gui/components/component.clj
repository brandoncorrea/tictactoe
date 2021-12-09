(ns tic-tac-toe.gui.components.component)

(defprotocol component
  (draw [this state])
  (update-state [this state]))

(defn draw-all [components state]
  (doseq [component components]
    (draw component state)))

(defn update-all [components state]
  (reduce #(update-state %2 %1) state components))
