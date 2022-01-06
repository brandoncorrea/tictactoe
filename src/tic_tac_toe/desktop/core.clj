(ns tic-tac-toe.desktop.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [ttt-data.datomic-db :as datomic]
            [tic-tac-toe.desktop.pages.page :as p]
            [tic-tac-toe.desktop.pages.new-game]
            [tic-tac-toe.desktop.pages.play]
            [tic-tac-toe.desktop.pages.game-over]
            [tic-tac-toe.desktop.pages.load-game]
            [tic-tac-toe.desktop.components.mouse :as mouse]
            [tic-tac-toe.desktop.state :as s]
            [tic-tac-toe.desktop.colors :as color]))

(def height 500)
(def width 500)

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (s/load-last-saved-game
    {:screen-height height
     :screen-width  width
     :mouse         (mouse/->mouse)
     :page          :new-game
     :db            (datomic/->datomic-db "datomic:free://localhost:4334/ttt-games-db")}))

(defn update-state [{mouse :mouse :as state}]
  (p/update-page (assoc state :mouse (mouse/update-mouse mouse))))

(defn draw-state [state]
  (q/background color/gray)
  (q/fill color/black)
  (q/text-align :center :center)
  (p/render-page state))

(q/defsketch gui
             :title "Tic Tac Toe"
             :size [width height]
             :setup setup
             :update update-state
             :draw draw-state
             :features [:keep-on-top]
             :middleware [m/fun-mode])

(defn -main [] gui)
