(ns tic-tac-toe.minimax
  (:require [tic-tac-toe.game-board :as board]))

(defn children [board token]
  (map #(assoc board % token) (board/empty-cells board)))

(defn- depth-factor [depth] (apply * (repeat depth 2)))
(defn- calculate-min [depth] (/ -1 (depth-factor depth)))
(defn- calculate-max [depth] (/ 1 (depth-factor depth)))
(defn- shortcut-max? [depth [value _]] (<= (calculate-max depth) value))
(defn- shortcut-min? [depth [value _]] (>= (calculate-min depth) value))

(declare greater-than?)
(defn maximum [a b] (if (greater-than? a b) a b))
(defn minimum [a b] (if (greater-than? a b) b a))

(defn- next-child [value children]
  (if (empty? children)
    [value children]
    (reduce maximum children)))

(defn greater-than? [a b]
  (loop [[v1 c1] a
         [v2 c2] b]
    (cond
      (< v1 v2) false
      (> v1 v2) true
      (and (empty? c1) (empty? c2)) false
      :else (recur (next-child v1 c1) (next-child v2 c2)))))

(defn value-of [results maximizing-token depth]
  [(cond (= (:winner results) maximizing-token) (calculate-max depth)
         (nil? (:winner results)) 0
         :else (calculate-min depth))
   []])

(declare minimax)

(defn maximize [board depth limit maximizing-token minimizing-token]
  (loop [[child & rest-children] (children board maximizing-token)
         max-value [-1 []]
         siblings []]
    (if (and child (< depth limit))
      (let [value (minimax child (inc depth) limit maximizing-token minimizing-token minimizing-token)]
        (if (shortcut-max? depth value)
          [(first value) []]
          (recur rest-children (maximum value max-value) (cons value siblings))))
      [(first max-value) siblings])))

(defn minimize [board depth limit maximizing-token minimizing-token]
  (loop [[child & rest-children] (children board minimizing-token)
         min-value [1 []]
         siblings []]
    (if (and child (< depth limit))
      (let [value (minimax child (inc depth) limit maximizing-token minimizing-token maximizing-token)]
        (if (shortcut-min? depth value)
          [(first value) []]
          (recur rest-children (minimum value min-value) (cons value siblings))))
      [(first min-value) siblings])))

(defn minimax [board depth limit maximizing-token minimizing-token cur-token]
  (let [results (board/game-results board)]
    (cond
      (:game-over results) (value-of results maximizing-token depth)
      (= maximizing-token cur-token)
        (maximize board depth limit maximizing-token minimizing-token)
      :else
        (minimize board depth limit maximizing-token minimizing-token))))

(defn optimal-move
  ([board maximizing-token minimizing-token]
   (optimal-move board maximizing-token minimizing-token 100))
  ([board maximizing-token minimizing-token limit]
   (loop [[cell & rest-cells] (board/empty-cells board)
          best-cell cell
          max-value [-1 []]]
     (if cell
       (let [evaluation (minimax (assoc board cell maximizing-token) 0 limit maximizing-token minimizing-token minimizing-token)]
         (cond
           (>= (first evaluation) 1) cell
           (greater-than? evaluation max-value) (recur rest-cells cell evaluation)
           :else (recur rest-cells best-cell max-value)))
       best-cell))))