(ns tic-tac-toe.minimax-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.minimax :refer :all]
            [tic-tac-toe.game-board :refer :all]))

(def empty-board (->board []))

(describe "children"
  (it "Empty board results in all cells"
    (should= (set (map #(assoc empty-board % \X)
                       [[0 0] [0 1] [0 2]
                        [1 0] [1 1] [1 2]
                        [2 0] [2 1] [2 2]]))
             (set (children (->board []) \X))))
  (it "Full board should result in no cells"
    (should= [] (children (->board (range 10)) \X))))

(describe "value-of"
  (it "Draw game results in zero"
    (should= [0 []] (value-of (game-results (->board (range 10))) \X 0)))
  (it "Draw game results in zero at any depth"
    (should= [0 []] (value-of (game-results (->board (range 10))) \X 5)))
  (it "Winning game results in one"
    (should= [1 []] (value-of (game-results (->board [\X \X \X])) \X 0)))
  (it "Losing game results in negative one"
    (should= [-1 []] (value-of (game-results (->board [\O \O \O])) \X 0)))
  (it "Winning board results in 1/2 with a depth of 1"
    (should= [1/2 []] (value-of (game-results (->board [\X \X \X])) \X 1)))
  (it "Winning board results in 1/4 with a depth of 2"
    (should= [1/4 []] (value-of (game-results (->board [\X \X \X])) \X 2)))
  (it "Losing board results in -1/2 with a depth of 1"
    (should= [-1/2 []] (value-of (game-results (->board [\O \O \O])) \X 1)))
  (it "Losing board results in -1/4 with a depth of 2"
    (should= [-1/4 []] (value-of (game-results (->board [\O \O \O])) \X 2))))

(describe "minimax"
  (it "Results in 1 for winning board"
    (should= [1 []] (minimax (->board [\X \X \X]) 0 100 \X \O \X)))
  (it "Results in -1 for losing board"
    (should= [-1 []] (minimax (->board [\O \O \O]) 0 100 \X \O \X)))
  (it "Results in 0 for draw board"
    (should= [0 []] (minimax (->board (range)) 0 100 \X \O \X)))
  (it "One empty space for a draw results in 0 with the one-cell mapping"
    (should= [0 [[0 []]]] (minimax (->board [nil \X \O
                                   \O  \X  \X
                                   \X \O \O]) 0 100 \X \O \X)))
  (it "Two empty spaces results in both cells' result mapping"
    (should= [0 [[0 [[0 []]]] [-1/4 [[-1/4 []]]]]]
             (minimax (->board [nil \X \O
                                \O  \X  \X
                                nil \O \O]) 0 100 \X \O \X))))

(describe "optimal-move"
  (it "Results in empty cell for two in row"
    (should= [0 0] (optimal-move (->board [nil \X \X]) \X \O)))
  (it "Results in blocking cell when opponent is about to win"
    (should= [1 0] (optimal-move (->board [\O nil nil
                                           nil nil nil
                                           \O nil \X]) \X \O)))
  (it "Results in winning cell when next move can win"
    (should= [1 2] (optimal-move (->board [\O nil \X nil nil nil \O nil \X]) \X \O)))
  (it "Blocks a square that will guarantee the opponent a win"
    (should-contain (optimal-move (->board [nil nil nil
                                            nil \X \O
                                            nil \O nil]) \X \O)
                    [[2 2] [2 0] [0 2]]))
  (it "Blocks a square that will guarantee the opponent a win"
    (should-contain (optimal-move (->board [\O nil nil
                                            nil \X \O
                                            nil \O \X]) \X \O)
                    [[0 2] [2 0]]))

  (it "Chooses best of available options - T - TOP"
    (should-contain (optimal-move (->board [\O \X \O
                                            nil \X nil
                                            nil \O nil]) \X \O)
                    [[1 2] [1 0]]))

  (it "Chooses best of available options - T - LEFT"
    (should-contain (optimal-move (->board [\O nil nil
                                             \X \X \O
                                             \O nil nil]) \X \O)
                    [[0 1] [2 1]]))

  (it "Chooses best of available options - T - BOTTOM"
    (should-contain (optimal-move (->board [nil \O nil
                                            nil \X nil
                                            \O \X \O]) \X \O)
                    [[1 2] [1 0]]))

  (it "Chooses best of available options - T - RIGHT"
    (should-contain (optimal-move (->board [nil nil \O
                                            \O \X \X
                                            nil nil \O]) \X \O)
                    [[0 1] [2 1]]))

  (it "Chooses best of available options - Dipper - Bottom-Right"
    (should-contain (optimal-move (->board [\O nil nil
                                             nil \X \O
                                             nil \O \X]) \X \O)
                     [[0 2] [2 0]]))

  (it "Chooses best of available options - Dipper - Bottom-Left"
    (should-contain (optimal-move (->board [nil nil \O
                                            \O \X nil
                                            \X \O nil]) \X \O)
                    [[0 0] [2 2]]))

  (it "Chooses best of available options - Dipper - Top-Right"
    (should-contain (optimal-move (->board [nil \O \X
                                            nil \X \O
                                            \O nil nil]) \X \O)
                    [[0 0] [2 2]]))

  (it "Chooses best of available options - Dipper - Top-Left"
    (should-contain (optimal-move (->board [\X \O nil
                                            \O \X nil
                                            nil nil \O]) \X \O)
                    [[0 2] [2 0]])))

(describe "maximum"
  (it "Equal draw values with no children results in either of the two values"
    (should= [0 []] (maximum [0 []] [0 []])))
  (it "Equal loss values with no children results in either of the two values"
    (should= [-1 []] (maximum [-1 []] [-1 []])))
  (it "Equal win values with no children results in either of the two values"
    (should= [1 []] (maximum [1 []] [1 []])))
  (it "Draw values where the first has a child win results in the first value"
    (should= [0 [[1 []]]] (maximum [0 [[1 []]]] [0 []])))
  (it "Draw values where the second has a child win results in the second value"
    (should= [0 [[1 []]]] (maximum [0 []] [0 [[1 []]]])))
  (it "Draw values where the first has a child win results in the first value"
    (should= [0 [[1 []]]] (maximum [0 [[1 []]]] [0 []])))
  (it "Draw values where the second has a nested child win results in the second value"
    (should= [0 [[0 [[1 []]]]]] (maximum [0 [[0 []]]] [0 [[0 [[1 []]]]]])))
  (it "Results in most beneficial value when top-level value is greater than nested values"
    (should= [1 []] (maximum [1 []] [0 [[1 []]]])))
  (it "Results in either of two equally nested values"
    (should= [0 [[0 []]]] (maximum [0 [[0 []]]] [0 [[0 []]]])))
  (it "Maximum can be reduced on a list of 1 item"
    (should= [0 [[[0 [[0 []]]] [0 [[0 []]]]]]]
             (reduce maximum [[0 [[[0 [[0 []]]] [0 [[0 []]]]]]]])))
  (it "Maximum reduced on list of 2 items results in the greater of the 2"
    (should= [0 [[1 []] [0 [[0 []]]]]]
             (reduce maximum [[0 [[0 [[0 []]]] [0 [[0 []]]]]]
                              [0 [[1 []] [0 [[0 []]]]]]])))
  (it "Maximum reduce on list of 4 items results in the greater of the 4"
    (should= [0 [[1 []] [0 [[0 []]]]]]
             (reduce maximum [[0 [[0 [[0 []]]] [0 [[0 []]]]]]
                              [0 [[1 []] [0 [[0 []]]]]]
                              [0 [[1 []] [0 [[0 []]]]]]
                              [0 [[0 [[0 []]]] [0 [[0 []]]]]]])))
  (it "Many nested values do not throw"
    (should= [0 [[0 []] [0 [[1 []] [1 []] [0 []]]] [-1 [[-1 []] [1 []]]]]]
             (maximum [0 [[0 []] [0 [[1 []] [1 []] [0 []]]] [-1 [[-1 []] [1 []]]]]]
                      [0 [[0 []] [0 []]]]))))

(describe "minimum"
  (it "Equal draw values with no children results in either of the two values"
    (should= [0 []] (minimum [0 []] [0 []])))
  (it "Equal loss values with no children results in either of the two values"
    (should= [-1 []] (minimum [-1 []] [-1 []])))
  (it "Equal win values with no children results in either of the two values"
    (should= [1 []] (minimum [1 []] [1 []])))
  (it "Draw values where the first has a child win results in the second value"
    (should= [0 []] (minimum [0 [[1 []]]] [0 []])))
  (it "Draw values where the second has a child win results in the first value"
    (should= [0 []] (minimum [0 []] [0 [[1 []]]])))
  (it "Draw values where the first has a child win results in the second value"
    (should= [0 []] (minimum [0 [[1 []]]] [0 []])))
  (it "Draw values where the second has a nested child win results in the first value"
    (should= [0 [[0 []]]] (minimum [0 [[0 []]]] [0 [[0 [[1 []]]]]])))
  (it "Draw values where the second has a nested child loss results in the second value"
    (should= [0 [[0 [[-1 []]]]]] (minimum [0 [[0 []]]] [0 [[0 [[-1 []]]]]])))
  (it "Results in least beneficial value when top-level value is greater than nested values"
    (should= [0 [[1 []]]] (minimum [1 []] [0 [[1 []]]])))
  (it "Results in least beneficial value when top-level value is lower than nested values"
    (should= [-1 []] (minimum [-1 []] [0 [[-1 []]]])))
  (it "Results in either of two equally nested values"
    (should= [0 [[0 []]]] (minimum [0 [[0 []]]] [0 [[0 []]]])))
  (it "Minimum can be reduced on a list of 1 item"
    (should= [0 [[0 [[0 []]]] [0 [[0 []]]]]]
             (reduce minimum [[0 [[0 [[0 []]]] [0 [[0 []]]]]]])))
  (it "Minimum reduced on list of 2 items results in the least of the 2"
    (should= [0 [[0 [[0 []]]] [0 [[0 []]]]]]
             (apply minimum [[0 [[0 [[0 []]]] [0 [[0 []]]]]]
                             [0 [[1 []] [0 [[0 []]]]]]])))
  (it "Minimum reduce on list of 4 items results in the least of the 4"
    (should= [0 [[0 [[0 []]]] [0 [[0 []]]]]]
             (reduce minimum [[0 [[0 [[0 []]]] [0 [[0 []]]]]]
                              [0 [[1 []] [0 [[0 []]]]]]
                              [0 [[1 []] [0 [[0 []]]]]]
                              [0 [[0 [[0 []]]] [0 [[0 []]]]]]]))))

(describe "greater-than?"
  (it "Results in false if the second value is more beneficial than the first"
    (should= false (greater-than? [0 [[0 [[0 []]]] [0 [[0 []]]]]]
                                  [0 [[1 []] [0 [[0 []]]]]])))
  (it "Results in true if the first value is more beneficial than the second"
    (should= true (greater-than? [0 [[1 []] [0 [[0 []]]]]]
                                 [0 [[0 [[0 []]]] [0 [[0 []]]]]])))
  (it "Results in false if both values are equal"
    (should= false (greater-than? [0 [[0 [[0 []]]] [0 [[0 []]]]]]
                                  [0 [[0 [[0 []]]] [0 [[0 []]]]]]))))