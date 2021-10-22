(ns tic-tac-toe.game-board)

(defn- ->column [board n]
  (map #(nth % n) board))
(defn- columns [board]
  (map #(->column board %) (range (count board))))
(defn- top-left-diagonal [board]
  (map #(nth (nth board %) %) (range (count board))))
(defn- top-right-diagonal [board]
  (top-left-diagonal (map reverse board)))

(defn- full-board? [board]
  (every? identity (flatten board)))

(defn- completed? [[first & rest]]
  (and (every? #(= first %) rest)
       first))

(defn ->board [cells]
  (vec (map vec (take 3 (partition 3 cells)))))

(defn mark-square [board position token]
  (update-in board position (fn [_] token)))

(defn- winning-token [board]
  (or (some completed? board)
      (some completed? (columns board))
      (completed? (top-left-diagonal board))
      (completed? (top-right-diagonal board))))

(defn game-over? [board]
  (if-let [winner (winning-token board)]
    {:draw false :winner winner}
    (if (full-board? board)
      {:draw true :winner nil})))
