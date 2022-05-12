(ns tic-tac-toe.web.pages.home
  (:require [hiccup.core :as h]
            [tic-tac-toe.web.response-util :as r]
            [ttt-core.game-board :as g]))

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
  [:div {:class "field"}
   [:div {:class "ui radio checkbox"}
    [:input {:type :radio :name name :value value :checked ""}]
    [:label text]]])

(defn ->rb [text name value]
  [:div {:class "field"}
   [:div {:class "ui radio checkbox"}
    [:input {:type :radio :name name :value value}]
    [:label text]]])

(def mode-options
  [:div {:class "inline fields"}
   [:label {:for :mode} "Game Mode:"]
   (->checked-rb "Player vs Player" :mode :player-vs-player)
   (->rb "Player vs Computer" :mode :player-vs-computer)])

(def size-options
  [:div {:class "inline fields"}
   [:label {:for :size} "Board Size:"]
   (->checked-rb "3x3" :size :3)
   (->rb "4x4" :size :4)])

(def difficulty-options
  [:div {:class "inline fields"}
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
  (h/html
    [:head
     [:link {:rel "stylesheet" :type "text/css"
             :href "https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.css"}]
     [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"}]
     [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.13/semantic.min.js"}]]
    [:body
     [:div {:class "ui padded center aligned container" :style "padding: 10px"}
      [:h1 {:class "ui header"} "Tic Tac Toe"]
      [:div {:class "ui message" :style "display: inline-block"} (create-title game)]
      [:form {:method "POST" :action "/move" :style "margin: 0 auto"}
       (->ttt-board board)]
      [:form {:method "POST" :action "/new-game" :class "ui form" :style "margin: 0 auto; display: inline-block; padding: 10px"}
       mode-options
       size-options
       difficulty-options
       [:button {:type :submit :class "fluid ui button"} "New Game"]]]]))

(defn render [game] (r/render-page (->html game)))
