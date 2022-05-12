(ns ttt-core.game-board-spec
  (:require [speclj.core :refer :all]
            [ttt-core.game-board :refer :all]
            [ttt-core.util.collections :refer [any]]))

(def empty-1x5
  {[0] nil [1] nil [2] nil [3] nil [4] nil})

(def empty-3x3
  {[0 0] nil [0 1] nil [0 2] nil
   [1 0] nil [1 1] nil [1 2] nil
   [2 0] nil [2 1] nil [2 2] nil})

(def empty-2x2x2
  {[0 0 0] nil [0 0 1] nil
   [0 1 0] nil [0 1 1] nil
   [1 0 0] nil [1 0 1] nil
   [1 1 0] nil [1 1 1] nil})

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

(def non-winning-paths
  [{[0 0] nil [0 1] nil [0 2] nil}
   {[0 0] \X [0 1] nil [0 2] nil}
   {[0 0] nil [0 1] \X [0 2] nil}
   {[0 0] nil [0 1] nil [0 2] \X}
   {[0 0] \X [0 1] \O [0 2] nil}
   {[0 0] \O [0 1] \X [0 2] nil}
   {[0 0] \X [0 1] nil [0 2] \O}
   {[0 0] \O [0 1] nil [0 2] \X}
   {[0 0] nil [0 1] \X [0 2] \O}
   {[0 0] nil [0 1] \O [0 2] \X}])

(def winning-paths
  [{[0 0] \X [0 1] \X [0 2] nil}
   {[0 0] \X [0 1] nil [0 2] \X}
   {[0 0] nil [0 1] \X [0 2] \X}
   {[1 0] \X [1 1] \X [1 2] nil}
   {[1 0] \X [1 1] nil [1 2] \X}
   {[1 0] nil [1 1] \X [1 2] \X}
   {[2 0] \X [2 1] \X [2 2] nil}
   {[2 0] \X [2 1] nil [2 2] \X}
   {[2 0] nil [2 1] \X [2 2] \X}
   {[0 0] \X [1 1] \X [2 2] nil}
   {[0 0] \X [1 1] nil [2 2] \X}
   {[0 0] nil [1 1] \X [2 2] \X}
   {[0 2] \X [1 1] \X [2 0] nil}
   {[0 2] \X [1 1] nil [2 0] \X}
   {[0 2] nil [1 1] \X [2 0] \X}])

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
               (->board [3 6 2] 5 1)))
    (it "Can initialize 2x2x2 board"
      (should= empty-2x2x2 (->board [] 2 3))
      (should= (assoc empty-2x2x2 [0 0 0] 3
                                  [0 0 1] 1
                                  [0 1 0] 5
                                  [0 1 1] 3)
               (->board [3 1 5 3] 2 3))))

  (describe "mark-square"
    (it "Player 1 marks the first square"
      (should= (->board [1])
               (mark-square empty-3x3 [0 0] 1)))
    (it "Player 2 marks the second square"
      (should= (->board [nil 2])
               (mark-square empty-3x3 [0 1] 2)))
    (it "Marks 4x4 board"
      (should= (merge empty-4x4 {[0 2] 2})
               (mark-square empty-4x4 [0 2] 2))))

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
               (set (series (->board (range) 4)))))
    (it "Returns series of 2x2x2 board"
      (should= #{; Exactly 1 differs
                 {[0 0 0] 0 [0 0 1] 1}
                 {[0 1 0] 2 [0 1 1] 3}
                 {[1 0 0] 4 [1 0 1] 5}
                 {[1 1 0] 6 [1 1 1] 7}

                 {[0 0 0] 0 [1 0 0] 4}
                 {[0 0 1] 1 [1 0 1] 5}
                 {[0 1 0] 2 [1 1 0] 6}
                 {[0 1 1] 3 [1 1 1] 7}

                 {[0 0 0] 0 [0 1 0] 2}
                 {[0 0 1] 1 [0 1 1] 3}
                 {[1 0 0] 4 [1 1 0] 6}
                 {[1 0 1] 5 [1 1 1] 7}

                 ; Exactly 2 differ
                 {[0 0 0] 0 [0 1 1] 3}
                 {[0 0 0] 0 [1 0 1] 5}
                 {[0 0 0] 0 [1 1 0] 6}
                 {[0 0 1] 1 [0 1 0] 2}
                 {[0 0 1] 1 [1 0 0] 4}
                 {[0 0 1] 1 [1 1 1] 7}
                 {[0 1 0] 2 [1 1 1] 7}
                 {[0 1 0] 2 [1 0 0] 4}
                 {[0 1 1] 3 [1 1 0] 6}
                 {[0 1 1] 3 [1 0 1] 5}
                 {[1 0 0] 4 [1 1 1] 7}
                 {[1 0 1] 5 [1 1 0] 6}

                 ; Exactly 3 differ
                 {[0 0 0] 0 [1 1 1] 7}
                 {[0 0 1] 1 [1 1 0] 6}
                 {[0 1 0] 2 [1 0 1] 5}
                 {[0 1 1] 3 [1 0 0] 4}}
               (set (series (->board (range) 2 3)))))
    (it "Returns series of 3x3x3 board"
      (should= #{
                 ; Face Rows
                 {[0 0 0] 0 [0 0 1] 1 [0 0 2] 2}
                 {[0 1 0] 3 [0 1 1] 4 [0 1 2] 5}
                 {[0 2 0] 6 [0 2 1] 7 [0 2 2] 8}

                 {[1 0 0] 9 [1 0 1] 10 [1 0 2] 11}
                 {[1 1 0] 12 [1 1 1] 13 [1 1 2] 14}
                 {[1 2 0] 15 [1 2 1] 16 [1 2 2] 17}

                 {[2 0 0] 18 [2 0 1] 19 [2 0 2] 20}
                 {[2 1 0] 21 [2 1 1] 22 [2 1 2] 23}
                 {[2 2 0] 24 [2 2 1] 25 [2 2 2] 26}

                 ; Pipes
                 {[0 0 0] 0 [1 0 0] 9 [2 0 0] 18}
                 {[0 0 1] 1 [1 0 1] 10 [2 0 1] 19}
                 {[0 0 2] 2 [1 0 2] 11 [2 0 2] 20}

                 {[0 1 0] 3 [1 1 0] 12 [2 1 0] 21}
                 {[0 1 1] 4 [1 1 1] 13 [2 1 1] 22}
                 {[0 1 2] 5 [1 1 2] 14 [2 1 2] 23}

                 {[0 2 0] 6 [1 2 0] 15 [2 2 0] 24}
                 {[0 2 1] 7 [1 2 1] 16 [2 2 1] 25}
                 {[0 2 2] 8 [1 2 2] 17 [2 2 2] 26}

                 ; Columns
                 {[0 0 0] 0 [0 1 0] 3 [0 2 0] 6}
                 {[0 0 1] 1 [0 1 1] 4 [0 2 1] 7}
                 {[0 0 2] 2 [0 1 2] 5 [0 2 2] 8}

                 {[1 0 0] 9 [1 1 0] 12 [1 2 0] 15}
                 {[1 0 1] 10 [1 1 1] 13 [1 2 1] 16}
                 {[1 0 2] 11 [1 1 2] 14 [1 2 2] 17}

                 {[2 0 0] 18 [2 1 0] 21 [2 2 0] 24}
                 {[2 0 1] 19 [2 1 1] 22 [2 2 1] 25}
                 {[2 0 2] 20 [2 1 2] 23 [2 2 2] 26}

                 ; Face Diagonals
                 {[0 0 0] 0 [0 1 1] 4 [0 2 2] 8}
                 {[0 0 2] 2 [0 1 1] 4 [0 2 0] 6}
                 {[1 0 0] 9 [1 1 1] 13 [1 2 2] 17}
                 {[1 0 2] 11 [1 1 1] 13 [1 2 0] 15}
                 {[2 0 0] 18 [2 1 1] 22 [2 2 2] 26}
                 {[2 0 2] 20 [2 1 1] 22 [2 2 0] 24}

                 ; Side Panel Diagonals
                 {[0 0 0] 0 [1 1 0] 12 [2 2 0] 24}
                 {[0 0 1] 1 [1 1 1] 13 [2 2 1] 25}
                 {[0 0 2] 2 [1 1 2] 14 [2 2 2] 26}
                 {[2 0 0] 18 [1 1 0] 12 [0 2 0] 6}
                 {[2 0 1] 19 [1 1 1] 13 [0 2 1] 7}
                 {[2 0 2] 20 [1 1 2] 14 [0 2 2] 8}

                 ; Layered Diagonals
                 {[0 0 0] 0 [1 0 1] 10 [2 0 2] 20}
                 {[0 0 2] 2 [1 0 1] 10 [2 0 0] 18}
                 {[0 1 0] 3 [1 1 1] 13 [2 1 2] 23}
                 {[0 1 2] 5 [1 1 1] 13 [2 1 0] 21}
                 {[0 2 0] 6 [1 2 1] 16 [2 2 2] 26}
                 {[0 2 2] 8 [1 2 1] 16 [2 2 0] 24}

                 ; 3D Diagonals (Special)
                 {[0 0 0] 0 [1 1 1] 13 [2 2 2] 26}
                 {[0 0 2] 2 [1 1 1] 13 [2 2 0] 24}
                 {[0 2 0] 6 [1 1 1] 13 [2 0 2] 20}
                 {[0 2 2] 8 [1 1 1] 13 [2 0 0] 18}
                 }
               (set (series (->board (range) 3 3))))))

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
    (for [[dim size count] [[2 3 0]
                            [2 3 8]
                            [2 4 0]
                            [2 4 15]
                            [3 3 0]
                            [3 3 26]]]
      (it (format "Results in false for %dD %dx%d with %d initial values" dim size size count)
        (should= false (full-board? (->board (range count) size dim)))))
    (for [[dim size] [[2 3] [2 4] [3 3]]]
      (it (format "Results in true for %dD %dx%d" dim size size)
        (should= true (full-board? (->board (range (apply * (repeat dim size))) size dim))))))

  (describe "game-results"
    (for [init [[] [1] [1 2 1]]]
      (it (format "Results in Game Not Over for board initialized with %s" init)
        (should= game-not-over-result (game-results (->board init)))))
    (for [init [{[0 0] 1 [0 1] 1 [0 2] 1}
                {[1 0] 1 [1 1] 1 [1 2] 1}
                {[2 0] 1 [2 1] 1 [2 2] 1}
                {[0 0] 1 [1 0] 1 [2 0] 1}
                {[0 1] 1 [1 1] 1 [2 1] 1}
                {[0 2] 1 [1 2] 1 [2 2] 1}
                {[0 0] 1 [1 1] 1 [2 2] 1}
                {[0 2] 1 [1 1] 1 [2 0] 1}]]
      (it (format "Results in win with initial values: %s" init)
        (should= win-result (game-results (merge empty-3x3 init)))))
    (it "Full game board results in a draw"
      (should= draw-result (game-results (->board (range 1 10))))))

  (describe "valid-move?"
    (for [move invalid-moves]
      (it (format "%s is an invalid move" move)
        (should= false (valid-move? empty-3x3 move))))
    (for [move valid-moves]
      (it (format "%s is a valid when unoccupied and invalid when occupied" move)
        (should= true (valid-move? empty-3x3 move))
        (should= false (valid-move? (mark-square empty-3x3 move 1) move))))
    (it "[0 0] results in false for empty vector"
      (should= false (valid-move? [] [0 0])))
    (it "2D move results in false for 3D board"
      (should= false (valid-move? empty-3x3x3 [0 0])))
    (it "3D move is valid for 3D board"
      (should= true (valid-move? empty-3x3x3 [0 0 0]))))

  (describe "dimensions"
    (for [dim [1 2 3 4]]
      (it (format "Results in %d for %dD board" dim dim)
        (should= dim (dimensions (->board [] 3 dim))))))

  (describe "size"
    (it "Results in the dimension's root of the size of the board"
      (should= 5 (size empty-1x5))
      (should= 3 (size empty-3x3))
      (should= 3 (size empty-3x3x3))
      (should= 3 (size (->board [])))
      (should= 4 (size empty-4x4))
      (should= 4 (size (->board [] 4)))))

  (describe "->cell-key"
    (for [[pos dim size expected] [[0 0 0 []]
                                   [0 1 1 [0]]
                                   [1 1 1 [1]]
                                   [2 1 1 [2]]
                                   [5 1 3 [5]]
                                   [0 2 3 [0 0]]
                                   [2 2 3 [0 2]]
                                   [3 2 3 [1 0]]
                                   [7 2 3 [2 1]]
                                   [0 3 3 [0 0 0]]
                                   [1 3 3 [0 0 1]]
                                   [5 3 3 [0 1 2]]
                                   [22 3 3 [2 1 1]]
                                   [26 3 3 [2 2 2]]
                                   [0 4 3 [0 0 0 0]]
                                   [1 4 3 [0 0 0 1]]
                                   [4 4 3 [0 0 1 1]]
                                   [11 4 3 [0 1 0 2]]
                                   [35 4 3 [1 0 2 2]]
                                   [11 4 5 [0 0 2 1]]
                                   [64 4 5 [0 2 2 4]]]]
      (it (format "Position %d for %dD %dx%d results in expected" pos dim size size expected)
        (should= expected (->cell-key pos dim size)))))

  (describe "winning-cell"
    (it "Results in nil when there are no winning paths"
      (should= nil (winning-cell \X (series empty-3x3))))
    (for [path winning-paths]
      (it (format "Results in last empty cell for path: %s" path)
        (should= (any-empty-cell path)
                 (winning-cell \X (series (merge empty-3x3 path)))))))

  (describe "winning-path?"
    (for [[expected paths] [[true winning-paths] [false non-winning-paths]]]
      (for [path paths]
        (it (format "Results in %s for %s" expected path)
          (should= expected (winning-path? \X path)))))))
