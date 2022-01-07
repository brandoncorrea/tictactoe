(ns ttt-cljs.components.board-spec
  (:require-macros [speclj.core :refer [describe it should should=]])
  (:require [speclj.core]
            [ttt-cljs.components.board :as c]))

(describe "parse-value"
  (for [[x y :as cell] [[0 1] [1 2]]]
    (it (str "parses vector text value: " cell)
      (should= cell (c/parse-value (str x "," y))))))

(describe "cell"
  (it "contains styling properties"
    (should= {:width "5em"
              :height "5em"
              :border "solid #000 1px"
              :text-align "center"
              :vertical-align "middle"
              :line-height "5em"
              :background-color "#fff"}
             c/col-style))
  (for [cell [[0 0] [1 2]]
        token [\X \O nil]]
    (it (str "is a div .col element " (or token "nil") " token")
      (should= [:button {:class "col"
                         :value cell
                         :style c/col-style
                         :on-click c/cell-click}
                (or token "")]
               (c/cell [cell token])))))

(describe "row"
  (it "contains styling properties"
    (should= {:margin "0 auto"
              :width "fit-content"
              :block-size "fit-content"}
             c/row-style))
  (it "creates an empty row"
    (should= [:div {:class "row" :style c/row-style}] (c/row [])))
  (it "creates a row with one item"
    (should= [:div {:class "row" :style c/row-style} (c/cell [[0 0] \X])]
             (c/row [[[0 0] \X]])))
  (it "creates a row with two items"
    (should= [:div {:class "row" :style c/row-style}
              (c/cell [[0 0] \X])
              (c/cell [[0 1] \O])]
             (c/row [[[0 0] \X] [[0 1] \O]])))
  (it "sorts row by key"
    (should= [:div {:class "row" :style c/row-style}
              (c/cell [[0 0] \X])
              (c/cell [[0 1] \Y])
              (c/cell [[0 2] \Z])]
             (c/row [[[0 2] \Z] [[0 0] \X] [[0 1] \Y] ])))
  (it "is a vector"
    (should (vector? (c/row {[0 0] \X [0 1] \Y})))))

(describe "board"
  (it "creates empty board"
    (should= [:div {:class "container"}]
             (c/board {})))
  (it "creates board with one cell"
    (should= [:div {:class "container"}
              (c/row {[0 0] \X})]
             (c/board {[0 0] \X})))
  (it "creates board with two rows"
    (should= [:div {:class "container"}
              (c/row {[0 0] \X})
              (c/row {[1 0] \O})]
             (c/board {[0 0] \X [1 0] \O})))
  (it "sorts by row number"
    (should= [:div {:class "container"}
              (c/row {[0 0] \X})
              (c/row {[1 0] \Y [1 1] "YA"})
              (c/row {[2 0] \Z})]
             (c/board {[1 1] "YA" [0 0] \X [2 0] \Z [1 0] \Y})))
  (it "is a vector"
    (should (vector? (c/board {[0 0] \X [1 0] \Y})))))
