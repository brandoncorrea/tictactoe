(ns tic-tac-toe.desktop.components.component)

(defmulti draw (fn [c & _] (:type c)))
(defmulti update-state (fn [c & _] (:type c)))

(defn draw-all [components state]
  (doseq [component components]
    (draw component state)))

(defn update-all [components state]
  (reduce #(update-state %2 %1) state components))
