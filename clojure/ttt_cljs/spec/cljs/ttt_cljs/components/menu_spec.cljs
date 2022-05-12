(ns ttt-cljs.components.menu-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [ttt-cljs.components.menu :as m]
            [ttt-cljs.state :as s]))

(describe "radio button"
  (for [[text name id] [["button 1" :some-name :some-id]
                        ["another button" :second-name :also-id]]]
    (it (str "created with text: " text ", name: " name ", and id: " id)
      (should= [:div {:style {:padding "5px 10px"}}
                [:input {:type    "radio"
                         :name    name
                         :id      id
                         :checked false}]
                [:label text]]
               (m/->radio {:text text :name name :id id}))))
  (it "does not require id"
    (should= [:div {:style {:padding "5px 10px"}}
              [:input {:type    "radio"
                       :name    :hello-name
                       :checked false}]
              [:label "Hello!"]]
             (m/->radio {:text "Hello!" :name :hello-name})))
  (it "does not require name"
    (should= [:div {:style {:padding "5px 10px"}}
              [:input {:type    "radio"
                       :id      :hello-id
                       :checked false}]
              [:label "Hello!"]]
             (m/->radio {:text "Hello!" :id :hello-id})))
  (it "does not require text"
    (should= [:div {:style {:padding "5px 10px"}}
              [:input {:type    "radio"
                       :name    :hello-name
                       :id      :hello-id
                       :checked false}]
              [:label ""]]
             (m/->radio {:name :hello-name :id :hello-id})))
  (for [event [identity inc]]
    (it (str "allows for on-click option: " event)
      (should= [:div {:style {:padding "5px 10px"}}
                [:input {:type    "radio"
                         :name    :event-name
                         :id      :event-id
                         :on-click event
                         :checked false}]
                [:label "Some Event"]]
               (m/->radio {:text "Some Event" :name :event-name :id :event-id :on-click event}))))
  (for [value [:value-1 :value-2]]
    (it (str "includes value property: " value)
      (should= [:div {:style {:padding "5px 10px"}}
                [:input {:type    "radio"
                         :name    :value-name
                         :id      :value-id
                         :value   value
                         :checked false}]
                [:label "Some Value"]]
               (m/->radio {:text "Some Value" :value value :name :value-name :id :value-id}))))
  (for [[expected options] [[true [true "" "true" "false" []]]
                            [false [false nil]]]]
    (for [checked? options]
      (it (str "checked defaults to " expected " when :checked is " checked?)
        (should= [:div {:style {:padding "5px 10px"}}
                  [:input {:type    "radio"
                           :name    :checked-name
                           :id      :checked-id
                           :checked expected}]
                  [:label "checked text"]]
                 (m/->radio {:name :checked-name :id :checked-id :text "checked text" :checked checked?}))))))

(describe "game-mode"
  (it "renders PVP and PVC options"
    (should= [:div {:style {:display "flex"}}
              (m/->radio {:text     "Player vs Computer"
                          :on-click s/set-pvc
                          :checked (= :player-vs-computer (s/game-mode))})
              (m/->radio {:text "Player vs Player"
                          :on-click s/set-pvp
                          :checked (= :player-vs-player (s/game-mode))})]
             (m/game-mode))))

(describe "difficulty"
  (it "renders Easy, Medium, and Hard options"
    (should= [:div {:style {:display "flex"}}
              (m/->radio {:text "Easy"
                          :on-click s/set-easy
                          :checked (= :easy (s/difficulty))})
              (m/->radio {:text "Medium"
                          :on-click s/set-medium
                          :checked (= :medium (s/difficulty))})
              (m/->radio {:text "Hard"
                          :on-click s/set-hard
                          :checked (= :hard (s/difficulty))})]
             (m/difficulty))))

(describe "size"
  (it "renders 3x3 and 4x4 options"
    (should= [:div {:style {:display "flex"}}
              (m/->radio {:text "3x3 Board"
                          :on-click s/set-3x3
                          :checked (= 3 (s/size))})
              (m/->radio {:text    "4x4 Board"
                          :on-click s/set-4x4
                          :checked (= 4 (s/size))})]
             (m/size))))

(describe "new-game"
  (it "renders new game button"
    (should= [:div
              [:input {:type "button"
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
                       :on-click s/new-game}]]
             m/new-game)))

(describe "menu"
  (it "renders game-mode, difficulty, and size options"
    (should= [:div
              {:style
               {:margin     "0 auto"
                :width      "fit-content"
                :block-size "fit-content"}}
              (m/game-mode)
              (m/difficulty)
              (m/size)
              m/new-game]
             (m/menu))))
