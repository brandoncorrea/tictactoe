(ns tic-tac-toe.web.routes.new-game
  (:require [tic-tac-toe.game-board :as g]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.web.pages.home :as home]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.util.url :as url]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]))

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
  (home/render (data/last-saved-game db)))
