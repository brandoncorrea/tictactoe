(ns tic-tac-toe.web.pages.home
  (:require [hiccup.core :as h]
            [tic-tac-toe.web.response-util :as r]
            [tic-tac-toe.game-board :as g]))

(def cell-style
  "height: 5em; width: 5em; border: solid #000 1px; display: inline-block; background-color: #fff; vertical-align: middle")

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

(def mode-options
  [:div
   [:label {:for :mode} "Game Mode:"]
   (->checked-rb "Player vs Player" :mode :player-vs-player)
   (->rb "Player vs Computer" :mode :player-vs-computer)])

(def size-options
  [:div
   [:label {:for :size} "Board Size:"]
   (->checked-rb "3x3" :size :3)
   (->rb "4x4" :size :4)])

(def difficulty-options
  [:div
   [:label {:for :difficulty} "Difficulty:"]
   (->checked-rb "Easy" :difficulty :easy)
   (->rb "Medium" :difficulty :medium)
   (->rb "Hard" :difficulty :hard)])

(defn create-title [{:keys [board next-player]}]
  (let [{:keys [draw winner]} (g/game-results board)]
    (cond
      draw "Game Over, Draw!"
      winner (str "Game Over, " winner " wins!")
      :else (str (:token next-player) "'s turn"))))

(defn ->html [{board :board :as game}]
  (h/html [:body {:style "text-align: center; margin: 0 auto;"}
           [:h1 "Tic Tac Toe"]
           [:p (create-title game)]
           [:form {:method "POST" :action "/move"}
            (->ttt-board board)]
           [:form {:method "POST" :action "/new-game"}
            mode-options
            size-options
            difficulty-options
            [:button {:type :submit} "New Game"]]]))

(defn render [game] (r/render-page (->html game)))
