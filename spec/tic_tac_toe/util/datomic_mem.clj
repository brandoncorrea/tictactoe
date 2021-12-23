(ns tic-tac-toe.util.datomic-mem
  (:require [tic-tac-toe.data.datomic-db :as datomic-db]
            [datomic.api :as datomic]))

(def in-memory-uri "datomic:mem://ttt-test-db")

(defn recreate-db []
  (datomic/delete-database in-memory-uri)
  (datomic-db/->datomic-db in-memory-uri))
