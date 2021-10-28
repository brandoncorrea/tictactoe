(ns tic-tac-toe.minimax
  (:use [tic-tac-toe.game-board]
        [tic-tac-toe.collection-util]))

(defn- cell-empty? [[_ value]] (nil? value))
(defn- empty-cells [board]
  (map first (filter cell-empty? board)))

(defn children [board token]
  (map #(assoc board % token) (empty-cells board)))

(defn terminal? [board]
  (:game-over (game-results board)))

(defn- depth-factor [depth] (apply * (repeat depth 2)))
(defn- calculate-min [depth] (/ -1 (depth-factor depth)))
(defn- calculate-max [depth] (/ 1 (depth-factor depth)))
(defn- shortcut-max? [depth value] (<= (calculate-max depth) value))
(defn- shortcut-min? [depth value] (<= (calculate-min depth) value))

(defn value-of [winner maximizing-token depth]
  (cond (= winner maximizing-token) (calculate-max depth)
        (nil? winner) 0
        :else (calculate-min depth)))

(defn- fns-for-minimax [token maximizing-token]
  (if (= token maximizing-token)
    [max shortcut-max?]
    [min shortcut-min?]))

(defn minimax [board depth maximizing-token [token next-token]]
  (let [results (game-results board)]
    (if (:game-over results)
      (value-of (:winner results) maximizing-token depth)
      (let [[min-max shortcut?] (fns-for-minimax token maximizing-token)]
        (reduce
          #(if (shortcut? depth %2) (reduced %2) (min-max %1 %2))
          (map #(minimax % (inc depth) maximizing-token [next-token token])
               (children board token)))))))

(defn- reduce-optimal [board maximizing-token minimizing-token [max-cell max-value] cell]
  (let [value (minimax (assoc board cell maximizing-token) 0 maximizing-token [minimizing-token maximizing-token])]
    (cond
      (<= 1 value) (reduced [cell value])
      (< max-value value) [cell value]
      :else [max-cell max-value])))

(defn optimal-move [board maximizing-token minimizing-token]
  (->> (empty-cells board)
       (reduce (partial reduce-optimal board maximizing-token minimizing-token) [nil -2])
       first))