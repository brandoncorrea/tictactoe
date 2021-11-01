(ns tic-tac-toe.game-board
  (:use [tic-tac-toe.collection-util]))

(defn size [board] (int (Math/sqrt (count board))))

(defn- cell-empty? [[_ value]] (nil? value))
(defn empty-cells [board]
  (map first (filter cell-empty? board)))

(defn series [board]
  (let [size (dec (size board))]
    (concat
      (group-into ffirst board)
      (group-into #(second (first %)) board)
      [(filter-into (fn [[[r c] _]] (= r c)) board)]
      [(filter-into (fn [[[r c] _]] (= size (+ r c))) board)])))

(defn rows [board]
  (map sorted-values (sort-by first (group-into ffirst board))))

(defn full-board? [board]
  (every? identity (vals board)))

(defn- completed? [row]
  (let [[value & rest] (vals row)]
    (and (every? #(= value %) rest)
         value)))

(defn- ->cell [position value size]
  [[(quot position size) (rem position size)] value])

(defn- take-cells [cells size]
  (take (* size size) (concat cells (repeat nil))))

(defn ->board
  ([cells] (->board cells 3))
  ([cells size]
   (map-into #(->cell %1 %2 size) (range) (take-cells cells size))))

(defn mark-square [board cell token]
  (assoc board cell token))

(defn- winning-token [board]
  (some completed? (series board)))

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
