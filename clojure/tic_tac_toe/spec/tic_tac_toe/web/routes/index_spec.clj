(ns tic-tac-toe.web.routes.index-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.routes.index :refer :all]
            [ttt-core.game-board :as g]
            [ttt-data.datomic-db :as d]
            [ttt-data.core :as data]
            [ttt-core.player.human :as human]
            [ttt-core.player.player-dispatcher :as dispatcher]))

(def test-uri "datomic:mem://ttt-test-db")

(describe "last board"
  (it "should load if it is game over"
    (should= true (should-load? (g/->board [0 0 0]))))
  (it "should not load if it is 3D"
    (should= false (should-load? (g/->board [] 3 3))))
  (it "should load if the board is empty"
    (should= true (should-load? (g/->board []))))
  (it "should load if the board has moves, but no winner"
    (should= true (should-load? (g/->board [1 2 3]))))
  (it "should load if game is a draw"
    (should= true (should-load? (g/->board (range)))))
  (it "should not load if game is 1D"
    (should= false (should-load? (g/->board [] 1 1)))))

(defn ->player [type token] {:type type :token token})

(describe "load-board"
  (it "loads last board if empty"
    (let [db (d/rebuild-database test-uri)
          board (g/->board [])
          next-player (->player :human \X)
          second-player (->player :human \O)
          _ (data/save-game db board next-player second-player)
          loaded (load-board db)]
      (should= board (:board loaded))
      (should= next-player (:next-player loaded))
      (should= second-player (:second-player loaded))))

  (it "loads last game if board was full"
    (let [db (d/rebuild-database test-uri)
          board (g/->board (range))
          player-1 (->player :human \X)
          player-2 (->player :human \O)
          _ (data/save-game db board player-1 player-2)
          loaded (load-board db)]
      (should= board (:board loaded))
      (should= player-1 (:next-player loaded))
      (should= player-2 (:second-player loaded))))

  (it "loads empty board if there is no saved game"
    (let [db (d/rebuild-database test-uri)
          loaded (load-board db)]
      (should= (g/->board []) (:board loaded))
      (should= (human/->human \X) (:next-player loaded))
      (should= (dispatcher/->bot :medium \X \O 3) (:second-player loaded)))))
