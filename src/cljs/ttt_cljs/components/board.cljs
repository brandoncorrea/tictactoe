(ns ttt-cljs.components.board
  (:require [ttt-cljs.state :as s]))

(def col-style
  {:width "5em"
   :height "5em"
   :border "solid #000 1px"
   :text-align "center"
   :vertical-align "middle"
   :line-height "5em"
   :background-color "#fff"})

(def row-style
  {:margin "0 auto"
   :width "fit-content"
   :block-size "fit-content"})

(defn parse-value [value]
  [(js/parseInt (first value))
   (js/parseInt (last value))])

(defn cell-click [button]
  (-> button .-target .-value parse-value s/move))

(defn cell [[cell token]]
  [:button
   {:class    "col"
    :style    col-style
    :on-click cell-click
    :value    cell}
   (str token)])

(defn row [cells]
  (vec (concat
         [:div {:class "row" :style row-style}]
         (map cell (sort-by first cells)))))

(defn board [cells]
  (vec (concat
         [:div {:class "container"}]
         (map (comp row second) (sort-by first (group-by ffirst cells))))))
