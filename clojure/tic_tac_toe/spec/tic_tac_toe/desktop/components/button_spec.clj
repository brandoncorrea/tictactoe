(ns tic-tac-toe.desktop.components.button-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.desktop.components.button :refer :all]))

(defn ->click [down-x down-y up-x up-y ]
  {:up-x   up-x
   :up-y   up-y
   :down-x down-x
   :down-y down-y})

(describe "mouse"
  (it "clicks on 0 height and 0 width buttons if the coordinates are exact"
    (should= true (clicked? 0 0 0 0 (->click 0 0 0 0)))
    (should= true (clicked? 100 100 0 0 (->click 100 100 100 100))))
  (it "does not click on 0 height and 0 width buttons when any of the click coordinate misses by 1"
    (doseq [[dx dy ux uy] [[1 0 0 0]
                           [-1 0 0 0]
                           [0 1 0 0]
                           [0 -1 0 0]
                           [0 0 1 0]
                           [0 0 -1 0]
                           [0 0 0 1]
                           [0 0 0 -1]]]
      (should= false (clicked? 0 0 0 0 (->click dx dy ux uy)))))
  (it "clicks anywhere along the border of the button"
    (doseq [[x y] [[0 0]
                   [45 0]
                   [100 0]
                   [100 30]
                   [100 100]
                   [75 100]
                   [0 100]
                   [0 47]]]
      (should= true (clicked? 0 0 100 100 (->click x y x y)))))
  (it "clicks within the button"
    (should= true (clicked? 0 0 100 100 (->click 35 95 47 62)))))
