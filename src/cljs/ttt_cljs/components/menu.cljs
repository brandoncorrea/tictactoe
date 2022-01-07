(ns ttt-cljs.components.menu
  (:require [ttt-cljs.state :as s]))

(defn ->radio [{:keys [text checked id] :as opts}]
  [:div {:class "form-check form-check-inline"}
   [:input (merge {:class "form-check-input"
                   :type  "radio"
                   :checked (if checked true false)}
                  (dissoc opts :text :checked))]
   [:label
    (merge {:class "form-check-label"}
           (if id {:for id} {}))
    (or text "")]])

(defn game-mode []
  [:div
   (->radio
     {:name :game-mode
      :text "Player vs Computer"
      :value :player-vs-computer
      :on-click s/set-pvc
      :checked (= :player-vs-computer (s/game-mode))})
   (->radio
     {:name :game-mode
      :text "Player vs Player"
      :value :player-vs-player
      :on-click s/set-pvp
      :checked (= :player-vs-player (s/game-mode))})])

(defn difficulty []
  [:div
   (->radio {:name :difficulty
             :text "Easy"
             :value :easy
             :on-click s/set-easy
             :checked (= :easy (s/difficulty))})
   (->radio {:name :difficulty
             :text "Medium"
             :value :medium
             :on-click s/set-medium
             :checked (= :medium (s/difficulty))})
   (->radio {:name :difficulty
             :text "Hard"
             :value :hard
             :on-click s/set-hard
             :checked (= :hard (s/difficulty))})])

(defn size []
  [:div
   (->radio {:name    :size
             :text    "3x3 Board"
             :value   3
             :on-click s/set-3x3
             :checked (= 3 (s/size))})
   (->radio {:name :size
             :text "4x4 Board"
             :value 4
             :on-click s/set-4x4
             :checked (= 4 (s/size))})])

(def new-game
  [:div {:class "d-grid gap-2"}
   [:input {:type "button"
            :class "btn btn-success"
            :value "New Game"
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
