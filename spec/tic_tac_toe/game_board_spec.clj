(ns tic-tac-toe.game-board-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def empty-1x5
  {[0] nil [1] nil [2] nil [3] nil [4] nil})

(def empty-3x3
  {[0 0] nil [0 1] nil [0 2] nil
   [1 0] nil [1 1] nil [1 2] nil
   [2 0] nil [2 1] nil [2 2] nil})

(def empty-3x3x3
  {[0 0 0] nil [0 0 1] nil [0 0 2] nil
   [0 1 0] nil [0 1 1] nil [0 1 2] nil
   [0 2 0] nil [0 2 1] nil [0 2 2] nil
   [1 0 0] nil [1 0 1] nil [1 0 2] nil
   [1 1 0] nil [1 1 1] nil [1 1 2] nil
   [1 2 0] nil [1 2 1] nil [1 2 2] nil
   [2 0 0] nil [2 0 1] nil [2 0 2] nil
   [2 1 0] nil [2 1 1] nil [2 1 2] nil
   [2 2 0] nil [2 2 1] nil [2 2 2] nil})

(def empty-4x4
  {[0 0] nil [0 1] nil [0 2] nil [0 3] nil
   [1 0] nil [1 1] nil [1 2] nil [1 3] nil
   [2 0] nil [2 1] nil [2 2] nil [2 3] nil
   [3 0] nil [3 1] nil [3 2] nil [3 3] nil})

(def win-result {:draw false :game-over true :winner 1})
(def draw-result {:draw true :game-over true :winner nil})
(def game-not-over-result {:draw false :game-over false :winner nil})

(def invalid-moves
  [[] [0] [0 0 0] [3 2] [3 3] [2 3] [4 2] [2 4] [-1 0] [0 -1]])
(def valid-moves
  [[0 0] [1 1] [0 1] [1 0]])

(describe "game-board"
  (describe "->board"
    (it "Partitions 3x3 empty board"
      (should= empty-3x3 (->board (repeat nil))))
    (it "Partitions 3x3 board with all 1s"
      (should= {[0 0] 1 [0 1] 1 [0 2] 1
                [1 0] 1 [1 1] 1 [1 2] 1
                [2 0] 1 [2 1] 1 [2 2] 1}
               (->board (repeat 1))))
    (it "No cells results in an empty map"
      (should= empty-3x3 (->board [])))
    (it "Can be initialized with one cell"
      (should= (assoc empty-3x3 [0 0] 1) (->board [1])))
    (it "Can be initialized with two cells"
      (should= (merge empty-3x3 {[0 0] 1 [0 1] 2})
               (->board [1 2])))
    (it "Can be initialized with three cells"
      (should= (merge empty-3x3 {[0 0] 1 [0 1] 2 [0 2] 3})
               (->board [1 2 3])))
    (it "Can be initialized with four cells"
      (should= (merge empty-3x3 {[0 0] 1 [0 1] 2 [0 2] 3 [1 0] 4})
               (->board (range 1 5))))
    (it "Can be initialized with five cells"
      (should= (merge empty-3x3 {[0 0] 1 [0 1] 2 [0 2] 3 [1 0] 4 [1 1] 5})
               (->board (range 1 6))))
    (it "Can be initialized with six cells"
      (should= (merge empty-3x3 {[0 0] 1 [0 1] 2 [0 2] 3 [1 0] 4 [1 1] 5 [1 2] 6})
               (->board (range 1 7))))
    (it "Can be initialized with full board"
      (should= {[0 0] 1 [0 1] 2 [0 2] 3 [1 0] 4 [1 1] 5 [1 2] 6 [2 0] 7 [2 1] 8 [2 2] 9}
               (->board (range 1 10))))
    (it "Can initialize 4x4 board"
      (should= (merge empty-4x4 {[0 0] 1 [0 1] 2 [0 2] 3})
               (->board [1 2 3] 4)))
    (it "Can initialize 3x3x3 board"
      (should= empty-3x3x3 (->board [] 3 3))
      (should= (assoc empty-3x3x3 [0 0 0] 2
                                  [0 0 1] 8
                                  [0 0 2] 3)
               (->board [2 8 3] 3 3)))
    (it "Can initialize 1x5 board"
      (should= empty-1x5 (->board [] 5 1))
      (should= (assoc empty-1x5 [0] 3 [1] 6 [2] 2)
               (->board [3 6 2] 5 1))))

  (describe "mark-square"
    (it "Player 1 marks the first square"
      (should= (->board [1])
               (mark-square empty-3x3 [0 0] 1)))
    (it "Player 2 marks the second square"
      (should= (->board [nil 2])
               (mark-square empty-3x3 [0 1] 2)))
    (it "Marks 4x4 board"
      (should= (merge empty-4x4 {[0 2] 2})
               (mark-square empty-4x4 [0 2] 2)))
    )

  (describe "series"
    (it "Returns rows, columns, and diagonals as lists of key-value pairs"
      (should= (set [{[0 0] nil [0 1] nil [0 2] nil}
                     {[1 0] nil [1 1] nil [1 2] nil}
                     {[2 0] nil [2 1] nil [2 2] nil}
                     {[0 0] nil [1 0] nil [2 0] nil}
                     {[0 1] nil [1 1] nil [2 1] nil}
                     {[0 2] nil [1 2] nil [2 2] nil}
                     {[0 0] nil [1 1] nil [2 2] nil}
                     {[0 2] nil [1 1] nil [2 0] nil}])
               (set (series (->board []))))
      (should= (set [{[0 0] 0 [0 1] 1 [0 2] 2}
                     {[1 0] 3 [1 1] 4 [1 2] 5}
                     {[2 0] 6 [2 1] 7 [2 2] 8}
                     {[0 0] 0 [1 0] 3 [2 0] 6}
                     {[0 1] 1 [1 1] 4 [2 1] 7}
                     {[0 2] 2 [1 2] 5 [2 2] 8}
                     {[0 0] 0 [1 1] 4 [2 2] 8}
                     {[0 2] 2 [1 1] 4 [2 0] 6}])
               (set (series (->board (range))))))
    (it "Returns series of 4x4 board"
      (should= (set [{[0 0] 0 [0 1] 1 [0 2] 2 [0 3] 3}
                     {[1 0] 4 [1 1] 5 [1 2] 6 [1 3] 7}
                     {[2 0] 8 [2 1] 9 [2 2] 10 [2 3] 11}
                     {[3 0] 12 [3 1] 13 [3 2] 14 [3 3] 15}
                     {[0 0] 0 [1 0] 4 [2 0] 8 [3 0] 12}
                     {[0 1] 1 [1 1] 5 [2 1] 9 [3 1] 13}
                     {[0 2] 2 [1 2] 6 [2 2] 10 [3 2] 14}
                     {[0 3] 3 [1 3] 7 [2 3] 11 [3 3] 15}
                     {[0 0] 0 [1 1] 5 [2 2] 10 [3 3] 15}
                     {[0 3] 3 [1 2] 6 [2 1] 9 [3 0] 12}])
               (set (series (->board (range) 4))))))

  (describe "rows"
    (it "Returns row data for empty board"
      (should= (take 3 (partition 3 3 (repeat nil)))
               (rows (->board []))))
    (it "Returns ordered row data for board with values 1 - 9"
      (should= (take 3 (partition 3 3 (range 1 10)))
               (rows (->board (range 1 10)))))
    (it "Returns row data for empty 4x4"
      (should= (take 4 (partition 4 4 (repeat nil)))
               (rows empty-4x4)))
    (it "Returns ordered row data for board with values 1 - 16"
      (should= (take 4 (partition 4 4 (range 1 17)))
               (rows (->board (range 1 17) 4))))
    (it "Returns one row for 1D board"
      (should= [[nil nil nil nil nil]] (rows empty-1x5))
      (should= [[1 2 3 4 5]] (rows (->board [1 2 3 4 5] 5 1))))
    (it "Returns all rows for all pages in 3x3x3 board"
      (should= [[0 1 2] [3 4 5] [6 7 8]
                [9 10 11] [12 13 14] [15 16 17]
                [18 19 20] [21 22 23] [24 25 26]]
               (rows (->board (range) 3 3))))
    (it "Returns all rows for all pages in 3x3x3x3"
      (should= (partition 3 (range 81))
               (rows (->board (range 81) 3 4)))))

  (describe "full-board?"
    (it "Empty board results in false"
      (should= false (full-board? empty-3x3)))
    (it "Results in false when board is full except one cell"
      (should= false (full-board? (->board (range 8)))))
    (it "Results in true when there are no nil values"
      (should= true (full-board? (->board (range 9)))))
    (it "Empty 4x4 board results in false"
      (should= false (full-board? empty-4x4)))
    (it "Results in true when there are no nil values in 4x4"
      (should= true (full-board? (->board (range) 4))))
    (it "Results in false when there is one empty cell in 4x4"
      (should= false (full-board? (->board (range 1 16) 4)))))

  (describe "game-results"
    (it "Empty game board results in game not over"
      (should= game-not-over-result (game-results empty-3x3)))
    (it "Board with one filled square results in game not over"
      (should= game-not-over-result (game-results (->board [1]))))
    (it "Board with first three items as 1 results in a win"
      (should= win-result (game-results (->board [1 1 1]))))
    (it "Board with middle three items as 1 results in a win"
      (should= win-result (game-results (->board [nil nil nil 1 1 1]))))
    (it "Board with last three items as 1 results in a win"
      (should= win-result (game-results (->board (concat (repeat 6 nil) [1 1 1])))))
    (it "Board with first row filled with different values results in game not over"
      (should= game-not-over-result (game-results (->board [1 2 1]))))
    (it "Board with first column filled results in a win"
      (should= win-result (game-results (->board [1 nil nil 1 nil nil 1 nil nil]))))
    (it "Board with second column filled results in a win"
      (should= win-result (game-results (->board [nil 1 nil nil 1 nil nil 1 nil]))))
    (it "Board with last column filled results in a win"
      (should= win-result (game-results (->board [nil nil 1 nil nil 1 nil nil 1]))))
    (it "Board with top-left diagonal filled results in a win"
      (should= win-result (game-results (->board [1 nil nil nil 1 nil nil nil 1]))))
    (it "Board with top-right diagonal filled results in a win"
      (should= win-result (game-results (->board [nil nil 1 nil 1 nil 1 nil nil]))))
    (it "Full game board results in a draw"
      (should= draw-result (game-results (->board (range 1 10))))))

  (describe "valid-move?"
    (it "Results in true for empty cell"
      (should= true (valid-move? empty-3x3 [0 0])))
    (it "Results in false when first cell is occupied"
      (should= false (valid-move? (mark-square empty-3x3 [0 0] 1) [0 0])))
    (it "Results in false when last cell is occupied"
      (should= false (valid-move? (mark-square empty-3x3 [2 2] 1) [2 2])))
    (it "Invalid moves result in false"
      (loop [[move & rest-moves] invalid-moves]
        (when move
          (should= false (valid-move? empty-3x3 move))
          (recur rest-moves))))
    (it "Valid positions result in true for empty spaces and false for occupied spaces"
      (loop [[move & rest-moves] valid-moves]
        (when move
          (should= true (valid-move? empty-3x3 move))
          (should= false (valid-move? (mark-square empty-3x3 move 1) move))
          (recur rest-moves))))
    (it "[0 0] results in false for empty vector"
      (should= false (valid-move? [] [0 0]))))

  (describe "dimensions"
    (it "Results in the number of elements in the first key"
      (loop [[dim & rest] [1 2 3 4]]
        (when dim
          (should= dim (dimensions (->board [] 3 dim)))
          (recur rest)))))

  (describe "size"
    (it "Results in the dimension's root of the size of the board"
      (should= 5 (size empty-1x5))
      (should= 3 (size empty-3x3))
      (should= 3 (size empty-3x3x3))
      (should= 3 (size (->board [])))
      (should= 4 (size empty-4x4))
      (should= 4 (size (->board [] 4)))))

  (describe "->cell-key"
    (it "Results in empty vector for zero dimensions"
      (should= [] (->cell-key 0 0 0)))
    (it "Results in 1 dimensional positions"
      (should= [0] (->cell-key 0 1 1))
      (should= [1] (->cell-key 1 1 1))
      (should= [2] (->cell-key 2 1 1))
      (should= [5] (->cell-key 5 1 3)))
    (it "Results in 2 dimensional positions"
      (should= [0 0] (->cell-key 0 2 3))
      (should= [0 2] (->cell-key 2 2 3))
      (should= [1 0] (->cell-key 3 2 3))
      (should= [2 1] (->cell-key 7 2 3)))
    (it "Results in 3 dimensional positions"
      (should= [0 0 0] (->cell-key 0 3 3))
      (should= [0 0 1] (->cell-key 1 3 3))
      (should= [0 1 2] (->cell-key 5 3 3))
      (should= [2 1 1] (->cell-key 22 3 3))
      (should= [2 2 2] (->cell-key 26 3 3)))
    (it "Results in 4 dimensional positions"
      (should= [0 0 0 0] (->cell-key 0 4 3))
      (should= [0 0 0 1] (->cell-key 1 4 3))
      (should= [0 0 1 1] (->cell-key 4 4 3))
      (should= [0 1 0 2] (->cell-key 11 4 3))
      (should= [1 0 2 2] (->cell-key 35 4 3))
      (should= [0 0 2 1] (->cell-key 11 4 5))
      (should= [0 2 2 4] (->cell-key 64 4 5))))
  )
