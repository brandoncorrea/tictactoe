(ns ttt-core.player.randomly-blocking-ai-spec
  (:require [speclj.core :refer :all]
            [ttt-core.player.randomly-blocking-ai :refer :all]
            [ttt-core.player.player :refer :all]
            [ttt-core.game-board :as board]))

(def bot (->randomly-blocking-ai \X \O))
(def empty-board (board/->board []))

(def winning-paths
  [{[0 0] \X [0 1] \X [0 2] nil}
   {[0 0] \X [0 1] nil [0 2] \X}
   {[0 0] nil [0 1] \X [0 2] \X}])

(describe "->randomly-blocking-ai"
  (it "Associates with the token it was given"
    (should= \X (:token bot)))
  (it "Contains opponent's token"
    (should= \O (:opponent bot)))
  (it "Is of :type :blocking-random"
    (should= :randomly-blocking (:type bot))))

(describe "next-move"
  (it "Results in random cell when there is nothing to block"
    (loop [board empty-board
           [[cell value] & rest] (vec {[0 0] 0 [1 2] 2 [2 2] 3})]
      (should (contains? (set (board/empty-cells board))
                         (next-move bot board)))
      (if cell (recur (assoc board cell value) rest))))

  (for [path winning-paths]
    (it (format "Results in winning cell for path: %s" path)
      (should= (board/winning-cell (:token bot) (board/series (merge empty-board path)))
               (next-move bot (merge empty-board path)))))

  (it "Blocks the opponent half the time with a 30% deviation"
    (let [board (assoc empty-board [0 0] \O [0 1] \O)
          moves (take 100 (repeatedly (fn [] (next-move bot board))))
          blocking-moves (count (filter (partial = [0 2]) moves))]
      (should (<= 35 blocking-moves 65)))))
