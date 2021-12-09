(ns tic-tac-toe.gui.gui
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [tic-tac-toe.gui.pages.page :as p]
            [tic-tac-toe.gui.router :as r]
            [tic-tac-toe.gui.pages.new-game]
            [tic-tac-toe.gui.pages.play]
            [tic-tac-toe.gui.pages.game-over]
            [tic-tac-toe.gui.components.mouse :as mouse]
            [tic-tac-toe.data.datomic-db :as datomic]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  {:screen-height 500
   :screen-width 500
   :mouse (mouse/->mouse)
   :page :new-game
   :db (datomic/->datomic-db "datomic:free://localhost:4334/ttt-games-db")})

(defn update-state [{mouse :mouse :as state}]
  (-> (assoc state :mouse (mouse/update-mouse mouse))
      p/update-page
      r/route))

(defn draw-state [state]
  (q/background 255)
  (q/fill 0)
  (q/text-align :center :center)
  (p/render-page state))

(q/defsketch gui
             :title "Tic Tac Toe"
             :size [500 500]
             :setup setup
             :update update-state
             :draw draw-state
             :features [:keep-on-top]
             :middleware [m/fun-mode])

(defn -main [] gui)
