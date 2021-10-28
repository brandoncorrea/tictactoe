(ns tic-tac-toe.collection-util)

(defn map-into [f c1 c2]
  (into {} (map f c1 c2)))
(defn filter-into [pred coll]
  (into {} (filter pred coll)))
(defn group-into [f coll]
  (map #(into {} (second %)) (group-by f coll)))
