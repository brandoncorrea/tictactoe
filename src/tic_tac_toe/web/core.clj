(ns tic-tac-toe.web.core
  (:require [http-server.core :as h]
            [tic-tac-toe.data.datomic-db :as datomic-db]
            [tic-tac-toe.web.routes.index :as index]))

(defn create-routes [db]
  {:/ {:get (partial index/render db)}})

(defn -main []
  (println "Connecting to datomic...")
  (let [db (datomic-db/->datomic-db "datomic:free://localhost:4334/ttt-games-db")
        port 8080]
    (println "Now listening on localhost port" port)
    (h/listen (h/create-server port (create-routes db)))))
