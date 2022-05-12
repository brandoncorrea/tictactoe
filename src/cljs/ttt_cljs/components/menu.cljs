(ns ttt-cljs.components.menu
  (:require [ttt-cljs.state :as s]))

(defn ->radio [{:keys [text checked] :as opts}]
  [:div {:style {:padding "5px 10px"}}
   [:input (merge {:type  "radio"
                   :checked (if checked true false)}
                  (dissoc opts :text :checked))]
   [:label (or text "")]])

(defn game-mode []
  [:div {:style {:display "flex"}}
   (->radio
     {:text "Player vs Computer"
      :on-click s/set-pvc
      :checked (= :player-vs-computer (s/game-mode))})
   (->radio
     {:text "Player vs Player"
      :on-click s/set-pvp
      :checked (= :player-vs-player (s/game-mode))})])

(defn difficulty []
  [:div {:style {:display "flex"}}
   (->radio {:text "Easy"
             :on-click s/set-easy
             :checked (= :easy (s/difficulty))})
   (->radio {:text "Medium"
             :on-click s/set-medium
             :checked (= :medium (s/difficulty))})
   (->radio {:text "Hard"
             :on-click s/set-hard
             :checked (= :hard (s/difficulty))})])

(defn size []
  [:div {:style {:display "flex"}}
   (->radio {:text    "3x3 Board"
             :on-click s/set-3x3
             :checked (= 3 (s/size))})
   (->radio {:text "4x4 Board"
             :on-click s/set-4x4
             :checked (= 4 (s/size))})])

(def new-game
  [:div
   [:input {:type  "button"
            :value "New Game"
            :style {:display "block"
                    :width "100%"
                    :border "none"
                    :background-color "#04AA6D"
                    :color "#fff"
                    :padding "14px 28px"
                    :font-size "16px"
                    :cursor "pointer"
                    :border-radius "10px"
                    :text-align "center"}
            :on-click s/new-game}]])

(defn menu []
  [:div
   {:style
    {:margin     "0 auto"
     :width      "fit-content"
     :block-size "fit-content"}}
   (game-mode)
   (difficulty)
   (size)
   new-game])
