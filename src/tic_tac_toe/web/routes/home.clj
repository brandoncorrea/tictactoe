(ns tic-tac-toe.web.routes.home
  (:require [hiccup.core :as h]))

(def cell-style "height: 5em; width: 5em; border: solid #000 2px; display: inline-block")

(defn ->ttt-button [[k v]]
  [:button {:type :submit :name :cell :value k :style cell-style} (str v)])

(defn ->ttt-board [board]
  (let [rows (group-by ffirst (sort-by first board))]
    (for [[_ cols] rows]
      [:div
       (for [cell cols]
         (->ttt-button cell))])))

(defn ->checked-rb [text name value]
  [:input {:type :radio :name name :value value :checked true} text])
(defn ->rb [text name value]
  [:input {:type :radio :name name :value value} text])

(defn ->radio-group [title name options]
  (concat
    [:div [:label {:for name} title]]
    (if-let [[text val] (first options)]
      [(->checked-rb text name val)]
      [])
    (for [[text val] (rest options)]
      (->rb text name val))))

(def game-mode-options
  (->radio-group "Game Mode:" :mode
                 [["Player vs Player" :player-vs-player]
                  ["Player vs Computer" :player-vs-computer]]))

(def size-options
  (->radio-group "Board Size:" :size
                 [["3x3" :3]
                  ["4x4" :4]]))

(def difficulty-options
  (->radio-group "Difficulty:" :difficulty
                 [["Easy" :easy]
                  ["Medium" :medium]
                  ["Hard" :hard]]))

(defn render [board]
  (h/html [:body {:style "text-align: center; margin: 0 auto;"}
           [:h1 "Tic Tac Toe"]
           [:form {:method "POST" :action "/move"}
            (->ttt-board board)]
           [:form {:method "POST" :action "/new-game"}
            game-mode-options
            size-options
            difficulty-options
            [:button {:type :submit :name :new-game} "New Game"]]]))
