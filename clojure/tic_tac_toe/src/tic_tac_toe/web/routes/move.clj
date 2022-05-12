(ns tic-tac-toe.web.routes.move
  (:require [ttt-data.core :as data]
            [tic-tac-toe.util.url :as url]
            [ttt-core.game :as g]
            [http-server.core :as h]))

(def cell (comp :cell url/decode first :body))

(defn render [db req]
  (let [game (g/move (data/last-saved-game db) (cell req))]
    (data/save-game db game)
    (h/create-response 303 {"Location" "/"})))
