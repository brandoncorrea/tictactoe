(ns tic-tac-toe.web.pages.home-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.pages.home :refer :all]
            [ttt-core.game-board :as b]))

(describe "radio-button"
  (it "can be generated with text, a name, and a value"
    (should= [:div {:class "field"}
              [:div {:class "ui radio checkbox"}
               [:input {:type :radio :name :abc :value "123"}]
               [:label "My RB"]]]
             (->rb "My RB" :abc "123"))
    (should= [:div {:class "field"}
              [:div {:class "ui radio checkbox"}
               [:input {:type :radio :name "name-here" :value :some-value}]
               [:label "Second Btn"]]]
             (->rb "Second Btn" "name-here" :some-value)))

  (it "can be generated with a checked state"
    (should= [:div {:class "field"}
              [:div {:class "ui radio checkbox"}
               [:input {:type :radio :name "val" :value 99 :checked ""}]
               [:label "My Checked RB"]]]
             (->checked-rb "My Checked RB" "val" 99))
    (should= [:div {:class "field"}
              [:div {:class "ui radio checkbox"}
               [:input {:type :radio :name :name :value :somethin :checked ""}]
               [:label "Another Radio Button"]]]
             (->checked-rb "Another Radio Button" :name :somethin))))

(describe "ttt-button"
  (it "converts cell to HTML submit button"
    (should= [:button {:type :submit :name :cell :value [0 0] :style cell-style} ""]
             (->ttt-button [[0 0] ""])))
  (it "converts nil values to empty strings"
    (should= [:button {:type :submit :name :cell :value [0 0] :style cell-style} ""]
             (->ttt-button [[0 0] nil])))
  (it "converts non-string values to strings"
    (should= [:button {:type :submit :name :cell :value [0 0] :style cell-style} "123"]
             (->ttt-button [[0 0] 123])))
  (it "value is the key of the cell"
    (should= [:button {:type :submit :name :cell :value \H :style cell-style} "321"]
             (->ttt-button [\H "321"]))))

(describe "title"
  (for [token [\X \O]]
    (it (format "displays player %s's move" token)
      (should= (str token "'s turn")
               (create-title {:next-player {:token token} :board (b/->board [])}))))
  (it "displays game over draw result"
    (should= "Game Over, Draw!"
             (create-title {:board (b/->board (range))})))
  (for [token [\X \O]]
    (it (format "displays win result for %s" token)
      (should= (str "Game Over, " token " wins!")
               (create-title {:board (b/->board [token token token])})))))

(describe "ttt-board"
  (it "generates 1x1 board"
    (should= [[:div [(->ttt-button [[0 0] nil])]]]
             (->ttt-board {[0 0] nil}))
    (should= [[:div [(->ttt-button [[0 0] \X])]]]
             (->ttt-board {[0 0] \X})))
  (it "generates 2x2 board"
    (should= [[:div
               [(->ttt-button [[0 0] nil])
                (->ttt-button [[0 1] nil])]]
              [:div
               [(->ttt-button [[1 0] nil])
                (->ttt-button [[1 1] nil])]]]
             (->ttt-board {[0 0] nil [0 1] nil [1 0] nil [1 1] nil})))
  (it "generates 3x3 board"
    (should= [[:div
               [(->ttt-button [[0 0] 0])
                (->ttt-button [[0 1] 1])
                (->ttt-button [[0 2] 2])]]
              [:div
               [(->ttt-button [[1 0] 3])
                (->ttt-button [[1 1] 4])
                (->ttt-button [[1 2] 5])]]
              [:div
               [(->ttt-button [[2 0] 6])
                (->ttt-button [[2 1] 7])
                (->ttt-button [[2 2] 8])]]]
             (->ttt-board {[0 0] 0 [0 1] 1 [0 2] 2
                           [1 0] 3 [1 1] 4 [1 2] 5
                           [2 0] 6 [2 1] 7 [2 2] 8}))))
