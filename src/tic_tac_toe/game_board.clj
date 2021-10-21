(ns tic-tac-toe.game-board)

(defn- ->column [board n] (map #(nth % n) board))

(defn- columns [board] (map #(->column board %) (range (count board))))

(defn- top-left-diagonal [board]
  (map #(nth (nth board %) %) (range (count board))))

(defn- top-right-diagonal [board]
  (top-left-diagonal (map reverse board)))

(defn- non-zero? [n] (not= 0 n))

(defn- completed? [[first & rest]]
  (and (non-zero? first)
       (every? #(= first %) rest)))

(defn ->board [cells]
  (vec (map vec (take 3 (partition 3 cells)))))

(defn mark-square [board position token]
  (update-in board position (fn [_] token)))

(defn game-over? [board]
  (or (some completed? board)
      (some completed? (columns board))
      (completed? (top-left-diagonal board))
      (completed? (top-right-diagonal board))
      (every? #(not= 0 %) (flatten board))))