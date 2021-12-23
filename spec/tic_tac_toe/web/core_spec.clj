(ns tic-tac-toe.web.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.core :refer :all]
            [tic-tac-toe.game-board :as g]
            [tic-tac-toe.data.data :as d]
            [datomic.api :as datomic]
            [tic-tac-toe.data.datomic-db :as datomic-db]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]))

(def empty-page (page ""))

(describe "page"
  (it "results in status code 200"
    (should= 200 (:status (empty-page nil))))
  (it "contains Content-Type text/html header"
    (should= "text/html" (get (:headers (empty-page nil)) "Content-Type")))
  (for [content ["Hello, world!" "Chicken noodle soup"]]
    (it (format "returns %s as bytes in body" content)
      (should= (map identity (.getBytes content))
               (map identity (:body ((page content) nil)))))))

(describe "last board"
  (it "should not load if it is game over"
    (should= false (should-load? (g/->board [0 0 0]))))
  (it "should not load if it is 3D"
    (should= false (should-load? (g/->board [] 3 3))))
  (it "should load if the board is empty"
    (should= true (should-load? (g/->board []))))
  (it "should load if the board has moves, but no winner"
    (should= true (should-load? (g/->board [1 2 3]))))
  (it "should not load if game is a draw"
    (should= false (should-load? (g/->board (range)))))
  (it "should not load if game is 1D"
    (should= false (should-load? (g/->board [] 1 1)))))

(defmethod d/last-saved-game :in-memory [db]
  (:last-save db))

(def in-memory-uri "datomic:mem://ttt-test-db")
(defn recreate-db []
  (datomic/delete-database in-memory-uri)
  (datomic-db/->datomic-db in-memory-uri))
(defn ->player [type token] {:type type :token token})

(describe "load-board"
  (it "loads last board if empty"
    (let [db (recreate-db)
          board (g/->board [])
          next-player (->player :human \X)
          second-player (->player :human \O)
          _ (data/save-game db board next-player second-player)
          loaded (load-board db)]
      (should= board (:board loaded))
      (should= next-player (:next-player loaded))
      (should= second-player (:second-player loaded))))

  (it "loads empty board if last game was full"
    (let [db (recreate-db)
          _ (data/save-game db (g/->board (range)) (->player :human \X) (->player :human \O))
          loaded (load-board db)]
      (should= (g/->board []) (:board loaded))
      (should= (human/->human \X) (:next-player loaded))
      (should= (dispatcher/->bot :medium \X \O 3) (:second-player loaded))))

  (it "loads empty board if there is no saved game"
    (let [db (recreate-db)
          loaded (load-board db)]
      (should= (g/->board []) (:board loaded))
      (should= (human/->human \X) (:next-player loaded))
      (should= (dispatcher/->bot :medium \X \O 3) (:second-player loaded)))))
