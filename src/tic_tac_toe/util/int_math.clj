(ns tic-tac-toe.util.int-math)

(defn nth-root [x n] (int (Math/pow x (/ 1.0 n))))
(defn pow [x n] (int (Math/pow x n)))