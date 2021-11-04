(ns tic-tac-toe.core
  (:require [tic-tac-toe.game-board :as board]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.ui.console-io :as console]
            [tic-tac-toe.player.player :as player]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.player.unbeatable-bot :as hard-bot]
            [tic-tac-toe.player.medium-bot :as medium-bot]
            [tic-tac-toe.player.easy-bot :as easy-bot]))

(defn- play [io player-1 player-2]
  (loop [board (board/->board [])
         [player next-player] [player-1 player-2]]
    (let [results (board/game-results board)]
      (if (:game-over results)
        (ui/show-results io board)
        (recur (board/mark-square board (player/next-move player board) (player/token player))
               [next-player player])))))

(defn ->bot [difficulty token-1 token-2]
  (cond
    (= difficulty :easy) (easy-bot/->EasyBot token-2)
    (= difficulty :medium) (medium-bot/->MediumBot token-2 token-1)
    :else (hard-bot/->UnbeatableBot token-2 token-1)))

(defn- new-game [io token-1 token-2]
  (if (= :player-vs-player (ui/request-game-mode io))
    (play io (human/->Human token-1 io) (human/->Human token-2 io))
    (play io (human/->Human token-1 io) (->bot (ui/request-difficulty io) token-1 token-2))))

(defn -main [& _]
  (let [io (console/->ConsoleIO)]
    (ui/show-title io)
    (ui/show-instructions io)
    (new-game io \X \O)))
