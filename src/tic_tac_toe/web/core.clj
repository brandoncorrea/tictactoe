(ns tic-tac-toe.web.core
  (:require [http-server.core :as h]
            [tic-tac-toe.web.routes.home :as home]
            [tic-tac-toe.data.datomic-db :as datomic-db]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.game-board :as g]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]))

(defn page [body]
  (fn [_] (h/create-response 200 body {"Content-Type" "text/html"})))

(defn should-load? [board]
  (and (= 2 (g/dimensions board))
       (not (:game-over (g/game-results board)))))

(defn- create-new-game [db]
  (data/save-game db (g/->board []) (human/->human \X) (dispatcher/->bot :medium \X \O 3))
  (data/last-saved-game db))

(defn load-board [db]
  (let [game (data/last-saved-game db)]
    (if (should-load? (:board game))
      game
      (create-new-game db))))

(defn create-routes [db]
  {:/ {:get (page (home/render (load-board db)))}})

(defn -main []
  (println "Connecting to datomic...")
  (let [db (datomic-db/->datomic-db "datomic:free://localhost:4334/ttt-games-db")
        port 8080]
    (println "Now listening on localhost port" port)
    (h/listen (h/create-server port (create-routes db)))))
