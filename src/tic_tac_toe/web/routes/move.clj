(ns tic-tac-toe.web.routes.move
  (:require [tic-tac-toe.web.pages.home :as home]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.util.url :as url]
            [tic-tac-toe.game :as g]))

(def cell (comp :cell url/decode first :body))

(defn render [db req]
  (let [game (g/move (data/last-saved-game db) (cell req))]
    (data/save-game db game)
    (home/render game)))
