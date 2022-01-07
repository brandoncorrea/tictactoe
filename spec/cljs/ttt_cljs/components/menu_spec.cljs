(ns ttt-cljs.components.menu-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [ttt-cljs.components.menu :as m]
            [ttt-cljs.state :as s]))

(describe "radio button"
  (for [[text name id] [["button 1" :some-name :some-id]
                        ["another button" :second-name :also-id]]]
    (it (str "created with text: " text ", name: " name ", and id: " id)
      (should= [:div {:class "form-check form-check-inline"}
                [:input {:class   "form-check-input"
                         :type    "radio"
                         :name    name
                         :id      id
                         :checked false}]
                [:label {:class "form-check-label" :for id} text]]
               (m/->radio {:text text :name name :id id}))))
  (it "does not require id"
    (should= [:div {:class "form-check form-check-inline"}
              [:input {:class   "form-check-input"
                       :type    "radio"
                       :name    :hello-name
                       :checked false}]
              [:label {:class "form-check-label"} "Hello!"]]
             (m/->radio {:text "Hello!" :name :hello-name})))
  (it "does not require name"
    (should= [:div {:class "form-check form-check-inline"}
              [:input {:class   "form-check-input"
                       :type    "radio"
                       :id      :hello-id
                       :checked false}]
              [:label {:class "form-check-label" :for :hello-id} "Hello!"]]
             (m/->radio {:text "Hello!" :id :hello-id})))
  (it "does not require text"
    (should= [:div {:class "form-check form-check-inline"}
              [:input {:class   "form-check-input"
                       :type    "radio"
                       :name    :hello-name
                       :id      :hello-id
                       :checked false}]
              [:label {:class "form-check-label" :for :hello-id} ""]]
             (m/->radio {:name :hello-name :id :hello-id})))
  (for [event [identity inc]]
    (it (str "allows for on-click option: " event)
      (should= [:div {:class "form-check form-check-inline"}
                [:input {:class   "form-check-input"
                         :type    "radio"
                         :name    :event-name
                         :id      :event-id
                         :on-click event
                         :checked false}]
                [:label {:class "form-check-label" :for :event-id} "Some Event"]]
               (m/->radio {:text "Some Event" :name :event-name :id :event-id :on-click event}))))
  (for [value [:value-1 :value-2]]
    (it (str "includes value property: " value)
      (should= [:div {:class "form-check form-check-inline"}
                [:input {:class   "form-check-input"
                         :type    "radio"
                         :name    :value-name
                         :id      :value-id
                         :value   value
                         :checked false}]
                [:label {:class "form-check-label" :for :value-id} "Some Value"]]
               (m/->radio {:text "Some Value" :value value :name :value-name :id :value-id}))))
  (for [[expected options] [[true [true "" "true" "false" []]]
                            [false [false nil]]]]
    (for [checked? options]
      (it (str "checked defaults to " expected " when :checked is " checked?)
        (should= [:div {:class "form-check form-check-inline"}
                  [:input {:class   "form-check-input"
                           :type    "radio"
                           :name    :checked-name
                           :id      :checked-id
                           :checked expected}]
                  [:label {:class "form-check-label" :for :checked-id} "checked text"]]
                 (m/->radio {:name :checked-name :id :checked-id :text "checked text" :checked checked?}))))))

(describe "game-mode"
  (it "renders PVP and PVC options"
    (should= [:div
              (m/->radio {:name     :game-mode
                          :text     "Player vs Computer"
                          :value    :player-vs-computer
                          :on-click s/set-pvc
                          :checked (= :player-vs-computer (s/game-mode))})
              (m/->radio {:name :game-mode
                          :text "Player vs Player"
                          :value :player-vs-player
                          :on-click s/set-pvp
                          :checked (= :player-vs-player (s/game-mode))})]
             (m/game-mode))))

(describe "difficulty"
  (it "renders Easy, Medium, and Hard options"
    (should= [:div
              (m/->radio {:name :difficulty
                          :text "Easy"
                          :value :easy
                          :on-click s/set-easy
                          :checked (= :easy (s/difficulty))})
              (m/->radio {:name :difficulty
                          :text "Medium"
                          :value :medium
                          :on-click s/set-medium
                          :checked (= :medium (s/difficulty))})
              (m/->radio {:name :difficulty
                          :text "Hard"
                          :value :hard
                          :on-click s/set-hard
                          :checked (= :hard (s/difficulty))})]
             (m/difficulty))))

(describe "size"
  (it "renders 3x3 and 4x4 options"
    (should= [:div
              (m/->radio {:name :size
                          :text "3x3 Board"
                          :value 3
                          :on-click s/set-3x3
                          :checked (= 3 (s/size))})
              (m/->radio {:name    :size
                          :text    "4x4 Board"
                          :value   4
                          :on-click s/set-4x4
                          :checked (= 4 (s/size))})]
             (m/size))))

(describe "new-game"
  (it "renders new game button"
    (should= [:div {:class "d-grid gap-2"}
              [:input {:type "button"
                       :class "btn btn-success"
                       :value "New Game"
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
