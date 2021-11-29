(ns tic-tac-toe.resources.datomic-db-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.resources.datomic-db :refer :all]
            [datomic.api :as d]
            [tic-tac-toe.resources.data :refer :all]
            [tic-tac-toe.game-board :as board]))

(def empty-3x3 (board/->board []))
(def in-memory-uri "datomic:mem://ttt-test-db")
(def bot-player {:type :unbeatable :token \X :opponent \O})
(def human-player {:type :human :token \X})

(defn recreate-db []
  (d/delete-database in-memory-uri)
  (->datomic-db in-memory-uri))

(describe "->datomic-db"
  (let [db (->datomic-db in-memory-uri)]
    (it "Associates type with map"
      (should= :datomic (:type db)))
    (it "Associates connection in map"
      (should= #{:type :connection} (set (keys db))))
    (it "Does not throw when created twice in a row"
      (should-not-throw (->datomic-db in-memory-uri))
      (should-not-throw (->datomic-db in-memory-uri)))

    (do (recreate-db)
        (for [n (range 5)]
          (it (format "Persists data for %d items" n)
            (should= n (count (find-all-games (->datomic-db in-memory-uri))))
            (save-game (->datomic-db in-memory-uri) {} {} {}))))))

(describe "last-saved-game"
  (it "Results in nil when no games have been played"
    (let [db (recreate-db)]
      (should= nil (last-saved-game db))))

  (it "Results in latest game as board updates"
    (let [db (recreate-db)]
      (loop [board empty-3x3
             [cur next] [bot-player human-player]
             [cell & rest] [[0 0] [1 0] [2 2] [1 1]]]
        (save-game db board cur next)
        (should= {:board board
                  :next-player cur
                  :second-player next}
                 (dissoc (last-saved-game db) :ts))
        (when cell
          (recur (assoc board cell (:token cur)) [next cur] rest))))))

(describe "save-game"
  (it "Adds game state"
    (let [db (recreate-db)]
      (save-game db empty-3x3 bot-player human-player)
      (should= {:board empty-3x3
                :next-player bot-player
                :second-player human-player}
               (dissoc (last-saved-game db) :ts)))))

(describe "find-all-games"
  (it "Contains :board, :turn, and :ts keys"
    (let [db (recreate-db)]
      (save-game db {} {} {})
      (should= #{:ts :board :next-player :second-player}
               (-> (find-all-games db) first keys set))))

  (it "Results in all saved games"
    (let [db (recreate-db)]
      (loop [[game & rest] [{:board empty-3x3
                             :next-player bot-player
                             :second-player human-player}
                            {:board (assoc empty-3x3 [1 1] \X)
                             :next-player bot-player
                             :second-player human-player}
                            {:board (assoc empty-3x3 [0 0] \X [1 0] \O)
                             :next-player human-player
                             :second-player bot-player}]
             added []]
        (should= (set added) (set (map #(dissoc % :ts) (find-all-games db))))
        (when game
          (save-game db (:board game) (:next-player game) (:second-player game))
          (recur rest (cons game added)))))))
