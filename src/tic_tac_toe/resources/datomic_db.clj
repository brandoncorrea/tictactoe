(ns tic-tac-toe.resources.datomic-db
  (:require [datomic.api :as d]
            [tic-tac-toe.resources.data :as data]))

(def ^:private schema (load-file "src/tic_tac_toe/resources/schema.edn"))

(defn- deserialize-game [[ts board next-player second-player]]
  {:ts    ts
   :board (read-string board)
   :next-player (read-string next-player)
   :second-player (read-string second-player)})

(defn ->datomic-db [uri]
  (d/create-database uri)
  (let [conn (d/connect uri)]
    (d/transact conn schema)
    {:type       :datomic
     :connection conn}))

(defmethod data/save-game :datomic [db board next-player second-player]
  (let [game-id (d/tempid :db.part/user)]
    @(d/transact (:connection db)
                 [{:db/id      game-id
                   :game/ts    (new java.util.Date)}
                  {:db/id      game-id
                   :game/board (str board)}
                  {:db/id        game-id
                   :game/next-player (str next-player)}
                  {:db/id        game-id
                   :game/second-player (str second-player)}])))

(defn- games [db]
  (d/q '[:find  ?ts ?game-board ?game-next-player ?game-second-player
         :where [?eid :game/ts ?ts]
                [?eid :game/board ?game-board]
                [?eid :game/next-player ?game-next-player]
                [?eid :game/second-player ?game-second-player]]
       (d/db (:connection db))))

(defmethod data/find-all-games :datomic [db]
  (map deserialize-game (games db)))

(defmethod data/last-saved-game :datomic [db]
  (last (sort-by :ts (data/find-all-games db))))
