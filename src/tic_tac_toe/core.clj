(ns tic-tac-toe.core
  (:require [tic-tac-toe.game-board :as board]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.ui.console-io :as console]
            [tic-tac-toe.player.player :as player]
            [tic-tac-toe.player.random-ai :as random-ai]
            [tic-tac-toe.player.medium-bot :as medium-bot]
            [tic-tac-toe.player.randomly-blocking-ai :as blocking-ai]
            [tic-tac-toe.player.advanced-blocking-ai :as advanced-ai]
            [tic-tac-toe.player.unbeatable-ai :as unbeatable-ai]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.resources.data :as data]
            [tic-tac-toe.resources.datomic-db :as datomic-db]))

(defn- play [io db board player-1 player-2]
  (loop [board board
         [player next-player] [player-1 player-2]]
    (data/save-game db board player next-player)
    (let [results (board/game-results board)]
      (if (:game-over results)
        (ui/show-results io board)
        (recur (board/mark-square board (player/next-move player board) (:token player))
               [next-player player])))))

(defn ->bot [difficulty token-1 token-2 dimensions]
  (cond
    (= difficulty :easy) (random-ai/->random-ai token-2)
    (< dimensions 3)
      (if (= difficulty :medium)
        (medium-bot/->medium-bot token-2 token-1)
        (unbeatable-ai/->unbeatable-ai token-2 token-1))
    :else
      (if (= difficulty :medium)
        (blocking-ai/->randomly-blocking-ai token-2 token-1)
        (advanced-ai/->advanced-blocking-ai token-2 token-1))))

(defn- player-vs-player? [io]
  (= :player-vs-player (ui/request-game-mode io)))

(defn- new-game [io db token-1 token-2]
  (let [size (ui/request-board-size io)
        dimensions (ui/request-board-dimensions io size)]
    (play io db
          (board/->board [] size dimensions)
          (human/->human token-1 io)
          (if (player-vs-player? io)
            (human/->human token-2 io)
            (->bot (ui/request-difficulty io) token-1 token-2 dimensions)))))

(defn- can-resume? [game]
  (and game
       (not (:game-over (board/game-results (:board game))))))

(defn- resume [io db game]
  (play io db (:board game) (:next-player game) (:second-player game)))

(defn -main [& _]
  (let [io (console/->ConsoleIO)
        db (datomic-db/->datomic-db "datomic:mem://ttt-test-db")
        game (data/last-saved-game db)]
    (ui/show-title io)
    (ui/show-instructions io)
    (if (can-resume? game)
      (resume io db game)
      (new-game io db \X \O))))
