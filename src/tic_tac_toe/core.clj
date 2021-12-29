(ns tic-tac-toe.core
  (:require [tic-tac-toe.game-board :as board]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.ui.console-io :as console]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.data.datomic-db :as datomic-db]
            [tic-tac-toe.game :as g]))

(def datomic-uri "datomic:free://localhost:4334/ttt-games-db")

(defn- play [io db game]
  (loop [{:keys [board next-player] :as game} game]
    (data/save-game db game)
    (if (g/game-over? game)
      (ui/show-results io board)
      (recur (g/move game (ui/request-move io board next-player))))))

(defn- player-vs-player? [io]
  (= :player-vs-player (ui/request-game-mode io)))

(defn- create-game [io db token-1 token-2]
  (let [size (ui/request-board-size io)
        dimensions (ui/request-board-dimensions io size)]
    (data/save-game db
                    (board/->board [] size dimensions)
                    (human/->human token-1)
                    (if (player-vs-player? io)
                      (human/->human token-2)
                      (dispatcher/->bot (ui/request-difficulty io) token-1 token-2 size dimensions)))))

(defn- should-resume? [game io]
  (and (g/can-resume? game)
       (ui/resume-game? io)))

(defn -main [& _]
  (let [io (console/->consoleIO)
        db (datomic-db/->datomic-db datomic-uri)
        game (data/last-saved-game db)]
    (ui/show-title io)
    (ui/show-instructions io)
    (if-not (should-resume? game io)
      (create-game io db \X \O))
    (play io db (data/last-saved-game db))
    (data/disconnect db)))
