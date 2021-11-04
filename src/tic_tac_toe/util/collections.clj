(ns tic-tac-toe.util.collections)

(defn map-into
  [f c1 c2] (into {} (map f c1 c2)))
(defn filter-into [pred coll]
  (into {} (filter pred coll)))
(defn group-into [f coll]
  (map #(into {} (second %)) (group-by f coll)))
(defn sorted-values [m] (-> m sort vals))
(defn find-first [pred coll]
  (first (filter pred coll)))
