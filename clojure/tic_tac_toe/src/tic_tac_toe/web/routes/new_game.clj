(ns tic-tac-toe.web.routes.new-game
  (:require [ttt-core.game-board :as g]
            [ttt-core.player.human :as human]
            [ttt-data.core :as data]
            [tic-tac-toe.util.url :as url]
            [ttt-core.player.player-dispatcher :as dispatcher]
            [http-server.core :as h]))

(defn- dispatch [{:keys [size mode difficulty]}]
  (dispatcher/->opponent mode difficulty size \X \O))

(defn- new-game [db {size :size :as options}]
  (data/save-game
    db
    (g/->board [] size)
    (human/->human \X)
    (dispatch options)))

(defn render [db req]
  (new-game db (url/decode (first (:body req))))
  (h/create-response 303 {"Location" "/"}))
