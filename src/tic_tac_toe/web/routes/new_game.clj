(ns tic-tac-toe.web.routes.new-game
  (:require [tic-tac-toe.game-board :as g]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.web.pages.home :as home]
            [tic-tac-toe.data.data :as data]
            [ring.util.codec :as r]
            [clojure.walk :as w]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]))

(defn- dispatch [{:keys [size mode difficulty]}]
  (dispatcher/->opponent (keyword mode) (keyword difficulty) size \X \O))

(defn- read-body [req]
  (let [{:keys [size mode difficulty] :as options}
        (w/keywordize-keys (r/form-decode (first (:body req))))]
    (assoc options :size (Integer/parseInt size)
                   :mode (keyword mode)
                   :difficulty (keyword difficulty))))

(defn- new-game [db {size :size :as options}]
  (data/save-game
    db
    (g/->board [] size)
    (human/->human \X)
    (dispatch options)))

(defn render [db req]
  (new-game db (read-body req))
  (home/render (data/last-saved-game db)))
