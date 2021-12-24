(ns tic-tac-toe.web.core
  (:require [http-server.core :as h]
            [tic-tac-toe.data.datomic-db :as datomic-db]
            [tic-tac-toe.web.routes.index :as index]
            [tic-tac-toe.web.routes.move :as move]
            [tic-tac-toe.web.routes.new-game :as new-game]))

(defn create-routes [db]
  {:* {:get (partial index/render db)}
   :new-game {:post (partial new-game/render db)}
   :move {:post (partial move/render db)}})

(defn -main []
  (println "Connecting to datomic...")
  (let [db (datomic-db/->datomic-db "datomic:free://localhost:4334/ttt-games-db")
        port 8080]
    (println "Now listening on localhost port" port)
    (h/listen (h/create-server port (create-routes db)))))
