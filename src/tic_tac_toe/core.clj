(ns tic-tac-toe.core
  (:require [tic-tac-toe.game-board :as board]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.ui.console-io :as console]
            [tic-tac-toe.player.player :as player]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.data.datomic-db :as datomic-db]))

(def datomic-uri "datomic:free://localhost:4334/ttt-games-db")

(defn player-move [io board player]
  (if (= :human (:type player))
    (ui/request-move io board player)
    (player/next-move player board)))

(defn- play [io db id board player-1 player-2]
  (loop [board board
         [player next-player] [player-1 player-2]]
    (data/save-game db board player next-player id)
    (let [results (board/game-results board)]
      (if (:game-over results)
        (ui/show-results io board)
        (recur (board/mark-square board (player-move io board player) (:token player)) [next-player player])))))

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
  (and game
       (-> game :board board/game-results :game-over not)
       (ui/resume-game? io)))

(defn- resume [io db game]
  (play io db
        (:id game)
        (:board game)
        (:next-player game)
        (:second-player game)))

(defn -main [& _]
  (let [io (console/->consoleIO)
        db (datomic-db/->datomic-db datomic-uri)
        game (data/last-saved-game db)]
    (ui/show-title io)
    (ui/show-instructions io)
    (if-not (should-resume? game io)
      (create-game io db \X \O))
    (resume io db (data/last-saved-game db))
    (data/disconnect db)))
