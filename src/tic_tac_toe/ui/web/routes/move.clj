(ns tic-tac-toe.ui.web.routes.move
  (:require [tic-tac-toe.data.data :as data]
            [tic-tac-toe.util.url :as url]
            [tic-tac-toe.game :as g]
            [http-server.core :as h]))

(def cell (comp :cell url/decode first :body))

(defn render [db req]
  (let [game (g/move (data/last-saved-game db) (cell req))]
    (data/save-game db game)
    (h/create-response 303 {"Location" "/"})))
