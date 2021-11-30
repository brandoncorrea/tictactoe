(ns tic-tac-toe.data.datomic-db
  (:require [datomic.api :as d]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.game-board :as board]))

(def ^:private schema (load-file "src/tic_tac_toe/data/schema.edn"))

(defn- deserialize-game [[id ts board next-player second-player]]
  {:id            id
   :ts            ts
   :board         (read-string board)
   :next-player   (read-string next-player)
   :second-player (read-string second-player)})

(defn ->datomic-db [uri]
  (d/create-database uri)
  (let [conn (d/connect uri)]
    (d/transact conn schema)
    {:type       :datomic
     :connection conn}))

(defmethod data/save-game :datomic
  ([db board next-player second-player]
   (data/save-game db board next-player second-player (d/tempid :db.part/user)))
  ([{conn :connection} board next-player second-player id]
   @(d/transact conn
                [{:db/id              id
                  :game/ts            (new java.util.Date)
                  :game/board         (str board)
                  :game/next-player   (str next-player)
                  :game/second-player (str second-player)}])))

(defn- games [{conn :connection}]
  (d/q '[:find  ?eid ?ts ?board ?next-player ?second-player
         :where [?eid :game/ts ?ts]
                [?eid :game/board ?board]
                [?eid :game/next-player ?next-player]
                [?eid :game/second-player ?second-player]]
       (d/db conn)))

(defmethod data/find-all-games :datomic [db]
  (map deserialize-game (games db)))

(defn- incomplete? [game]
  (-> game :board board/game-results :game-over not))

(defmethod data/incomplete-games :datomic [db]
  (filter incomplete? (data/find-all-games db)))

(defmethod data/last-saved-game :datomic [db]
  (last (sort-by :ts (data/find-all-games db))))

(defmethod data/disconnect :datomic [{conn :connection}]
  (d/release conn)
  (d/shutdown true))
