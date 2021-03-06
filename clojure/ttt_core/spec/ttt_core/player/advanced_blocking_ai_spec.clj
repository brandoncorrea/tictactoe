(ns ttt-core.player.advanced-blocking-ai-spec
  (:require [speclj.core :refer :all]
            [ttt-core.player.player :refer :all]
            [ttt-core.player.advanced-blocking-ai :refer :all]
            [ttt-core.game-board :as board]
            [ttt-core.util.collections :refer :all]))

(def bot (->advanced-blocking-ai \X \O))
(def empty-3x3 (board/->board []))

(describe "->advanced-blocking-ai"
  (it "Associates with the token it was given"
    (should= \X (:token bot)))
  (it "Contains opponent's token"
    (should= \O (:opponent bot)))
  (it "Is of :type :blocking-random"
    (should= :advanced-blocking (:type bot))))

(describe "next-move"
  (it "Chooses any cell when board is empty"
    (should (contains? (set (keys empty-3x3)) (next-move bot empty-3x3))))
  (it "Does not result in nil"
    (should-not= nil (next-move bot (merge (board/->board [] 3 3)
                                         {[0 0 0] \O
                                          [0 0 1] \O
                                          [0 0 2] \X
                                          [1 1 2] \O
                                          [2 2 2] \X}))))
  (it "Chooses a cell in which it already has a token"
    (should (contains? #{[0 1] [0 2] [1 0] [2 0] [1 1] [2 2]}
                       (next-move bot (board/mark-square empty-3x3 [0 0] \X))))))

(describe "greatest"
  (it "Results in the winning path"
    (let [path-1 {[0 0] nil [0 1] :x [0 2] :x}
          path-2 {[1 0] nil [1 1] :o [2 2] :o}]
      (should= path-1 (greatest :x path-1 path-2))
      (should= path-2 (greatest :o path-1 path-2))))
  (it "Results in losing path if player cannot win"
    (let [path-1 {[0 0] nil [0 1] :x [0 2] nil}
          path-2 {[1 0] :o [1 1] :o [1 2] nil}]
      (should= path-2 (greatest :x path-1 path-2))
      (should= path-2 (greatest :x path-2 path-1))
      (should= path-2 (greatest :o path-1 path-2))
      (should= path-2 (greatest :o path-2 path-1))))
  (it "Results in the path that it has most tokens in"
    (let [path-1 {[0 0] :x [0 1] nil [0 2] nil}
          path-2 {[1 0] nil [1 1] nil [1 2] nil}]
      (should= path-1 (greatest :x path-1 path-2))
      (should= path-1 (greatest :x path-2 path-1))))
  (it "Chooses the path the opponent does not occupy"
    (should= {[0 0] nil [0 1] nil [0 2] nil}
             (greatest :x
                       {[0 0] nil [0 1] nil [0 2] nil}
                       {[1 0] :o [1 1] nil [1 2] nil}))))
