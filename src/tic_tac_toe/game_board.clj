(ns tic-tac-toe.game-board)

(defn- map-cells [board rows cols]
  (map (fn [r c] (get-in board [r c])) rows cols))
(defn- map-board [board f]
  (map (partial f board) (range (count board))))

(defn nth-col [board c]
  (map-cells board (range (count board)) (repeat c)))
(defn nth-row [board r]
  (map-cells board (repeat r) (range (count board))))

(defn columns [board] (map-board board nth-col))
(defn rows [board] (map-board board nth-row))

(defn top-left-diagonal [board]
  (let [indices (range (count board))]
    (map-cells board indices indices)))

(defn top-right-diagonal [board]
  (let [indices (range (count board))]
    (map-cells board indices (reverse indices))))

(defn full-board? [board]
  (every? identity (mapcat #(map second (second %)) board)))

(defn- completed? [[first & rest]]
  (and (every? (partial = first) rest)
       first))

(defn- partition-cells [cells size]
  (take size (partition size size (concat cells (repeat nil)))))

(defn- ->dict [keys values]
  (into {} (map (fn [k v] [k v]) keys values)))

(defn- ->row [size row-data]
  (->dict (range size) row-data))

(defn- cells->rows [cells size]
  (map (partial ->row size) (partition-cells cells size)))

(defn ->board
  ([cells] (->board cells 3))
  ([cells size]
   (->dict (range size) (cells->rows cells size))))

(defn mark-square [board cell token]
  (assoc-in board cell token))

(defn- winning-token [board]
  (or (some completed? (rows board))
      (some completed? (columns board))
      (completed? (top-left-diagonal board))
      (completed? (top-right-diagonal board))))

(defn game-results [board]
  (if-let [winner (winning-token board)]
    {:draw false :game-over true :winner winner}
    (if (full-board? board)
      {:draw true :game-over true :winner nil}
      {:draw false :game-over false :winner nil})))

(defn valid-move? [board [row col & rest]]
  (let [found (get-in board [row col] :not-found)]
    (and (empty? rest)
         (not= found :not-found)
         (nil? found))))
