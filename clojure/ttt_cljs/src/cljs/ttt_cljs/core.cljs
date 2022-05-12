(ns ttt-cljs.core
  (:require [reagent.dom :as dom]
            [ttt-cljs.app :as a]))

(defn ^:export main []
  (dom/render [a/app] (js/document.getElementById "app")))

(main)