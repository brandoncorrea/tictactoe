(ns tic-tac-toe.game-board
  (:require [tic-tac-toe.util.collections :as util]
            [tic-tac-toe.util.int-math :as math]))

(defn dimensions [board] (count (ffirst board)))
(defn size [board] (math/nth-root (count board) (dimensions board)))

(defn- cell-empty? [[_ value]] (nil? value))
(defn empty-cells [board]
  (map first (filter cell-empty? board)))

(defn series [board]
  (let [size (dec (size board))]
    (concat
      (util/group-into ffirst board)
      (util/group-into #(second (first %)) board)
      [(util/filter-into (fn [[[r c] _]] (= r c)) board)]
      [(util/filter-into (fn [[[r c] _]] (= size (+ r c))) board)])))

(defn rows [board]
  (map util/sorted-values (sort-by first (util/group-into ffirst board))))

(defn full-board? [board]
  (every? identity (vals board)))

(defn- completed-token [row]
  (let [[value & rest] (vals row)]
    (and (every? #(= value %) rest) value)))

(defn ->cell-key [position dimensions size]
  (loop [pos position
         cell []
         dim (dec dimensions)]
    (if (< dim 0)
      (vec (reverse cell))
      (recur (rem pos (math/pow size dim))
             (cons (quot pos (math/pow size dim)) cell)
             (dec dim)))))

(defn- ->cell [position dimensions size value]
  [(->cell-key position dimensions size) value])

(defn- take-cells [cells size dimensions]
  (take (math/pow size dimensions) (concat cells (repeat nil))))

(defn ->board
  ([cells] (->board cells 3 2))
  ([cells size] (->board cells size 2))
  ([cells size dimensions]
   (util/map-into #(->cell %1 dimensions size %2) (range) (take-cells cells size dimensions))))

(defn mark-square [board cell token]
  (assoc board cell token))

(defn- winning-token [board]
  (some completed-token (series board)))

(defn game-results [board]
  (if-let [winner (winning-token board)]
    {:draw false :game-over true :winner winner}
    (if (full-board? board)
      {:draw true :game-over true :winner nil}
      {:draw false :game-over false :winner nil})))

(defn valid-move? [board [row col & rest]]
  (let [found (get board [row col] :not-found)]
    (and (empty? rest)
         (nil? found)
         (not= found :not-found))))
