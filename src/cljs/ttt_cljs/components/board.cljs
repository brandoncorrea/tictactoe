(ns ttt-cljs.components.board)

(def col-style
  {:width "5em"
   :height "5em"
   :border "solid #000 1px"
   :text-align "center"
   :vertical-align "middle"
   :line-height "5em"})

(def row-style
  {:margin "0 auto"
   :width "fit-content"
   :block-size "fit-content"})

(defn cell [[_ token]] [:div {:class "col" :style col-style} (str token)])

(defn row [cells]
  (vec (concat
         [:div {:class "row" :style row-style}]
         (map cell (sort-by first cells)))))

(defn board [cells]
  (vec (concat
         [:div {:class "container"}]
         (map (comp row second) (sort-by first (group-by ffirst cells))))))
