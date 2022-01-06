(ns tic-tac-toe.ui.console.core
  (:require [ttt-core.game-board :as board]
            [tic-tac-toe.ui.console.io :as io]
            [ttt-core.player.player-dispatcher :as dispatcher]
            [ttt-core.player.human :as human]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.data.datomic-db :as datomic-db]
            [ttt-core.game :as g]))

(def datomic-uri "datomic:free://localhost:4334/ttt-games-db")

(defn- play [db game]
  (loop [{:keys [board next-player] :as game} game]
    (data/save-game db game)
    (if (g/game-over? game)
      (io/show-results board)
      (recur (g/move game (io/request-move board next-player))))))

(defn- player-vs-player? []
  (= :player-vs-player (io/request-game-mode)))

(defn- create-game [db token-1 token-2]
  (let [size (io/request-board-size)
        dimensions (io/request-board-dimensions size)]
    (data/save-game db
                    (board/->board [] size dimensions)
                    (human/->human token-1)
                    (if (player-vs-player?)
                      (human/->human token-2)
                      (dispatcher/->bot (io/request-difficulty) token-1 token-2 size dimensions)))))

(defn- should-resume? [game]
  (and (g/can-resume? game)
       (io/resume-game?)))

(defn -main []
  (let [db (datomic-db/->datomic-db datomic-uri)
        game (data/last-saved-game db)]
    (io/show-title)
    (io/show-instructions)
    (if-not (should-resume? game)
      (create-game db \X \O))
    (play db (data/last-saved-game db))
    (data/disconnect db)))
