(ns ttt-core.game-board
  (:require [ttt-core.util.collections :as util]
            [ttt-core.util.int-math :as math]))

(defn dimensions [board] (count (ffirst board)))
(defn size [board] (math/nth-root (count board) (dimensions board)))

(defn cell-empty? [[_ value]] (nil? value))
(defn empty-cells [board]
  (map first (filter cell-empty? board)))
(defn any-empty-cell [path]
  (first (util/any cell-empty? path)))

(defn winning-path? [token path]
  (let [values (vals path)
        unchecked-cells (filter (partial not= token) values)]
    (and (= 1 (count unchecked-cells))
         (nil? (first unchecked-cells)))))

(defn- winning-paths [token paths]
  (filter #(winning-path? token %) paths))

(defn winning-cell [token paths]
  (first (map any-empty-cell (winning-paths token paths))))

(defmulti series dimensions)

(defmethod series :default [board]
  (let [size (dec (size board))]
    (concat
      (util/group-into ffirst board)
      (util/group-into #(second (first %)) board)
      [(util/filter-into (fn [[[r c] _]] (= r c)) board)]
      [(util/filter-into (fn [[[r c] _]] (= size (+ r c))) board)])))

(defmethod series 3 [board]
  (let [size (dec (size board))]
    (concat
      (util/group-into #(take 2 (first %)) board)
      (util/group-into #(drop 1 (first %)) board)
      (util/group-into (fn [[[x _ z] _]] [x z]) board)
      (map (partial util/filter-into (fn [[[_ y z] _]] (= y z))) (util/group-into ffirst board))
      (map (partial util/filter-into (fn [[[_ y z] _]] (= size (+ y z)))) (util/group-into ffirst board))
      (map (partial util/filter-into (fn [[[x _ z] _]] (= x z))) (util/group-into #(second (first %)) board))
      (map (partial util/filter-into (fn [[[x _ z] _]] (= size (+ x z)))) (util/group-into #(second (first %)) board))
      (map (partial util/filter-into (fn [[[x y _] _]] (= x y))) (util/group-into #(last (first %)) board))
      (map (partial util/filter-into (fn [[[x y _] _]] (= size (+ x y)))) (util/group-into #(last (first %)) board))
      (if (= 1 size)
        [(into {} (filter (fn [[key _]] (contains? #{[0 1 0] [1 0 1]} key)) board))
         (into {} (filter (fn [[key _]] (contains? #{[0 1 1] [1 0 0]} key)) board))
         (into {} (filter (fn [[key _]] (contains? #{[0 0 0] [1 1 1]} key)) board))
         (into {} (filter (fn [[key _]] (contains? #{[0 0 1] [1 1 0]} key)) board))]
        [(into {} (filter (fn [[key _]] (contains? #{[0 2 0] [1 1 1] [2 0 2]} key)) board))
         (into {} (filter (fn [[key _]] (contains? #{[0 2 2] [1 1 1] [2 0 0]} key)) board))
         (into {} (filter (fn [[key _]] (contains? #{[0 0 0] [1 1 1] [2 2 2]} key)) board))
         (into {} (filter (fn [[key _]] (contains? #{[0 0 2] [1 1 1] [2 2 0]} key)) board))]))))

(defn rows [board]
  (partition (size board) (util/sorted-values board)))

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

(defn valid-move? [board cell]
  (let [found (get board cell :not-found)]
    (and (= (count cell) (dimensions board))
         (nil? found)
         (not= found :not-found))))
