(ns tic-tac-toe.core
  (:require [tic-tac-toe.game-board :as board]
            [tic-tac-toe.user-interface :as ui]
            [tic-tac-toe.player :as player]
            [tic-tac-toe.console-io :as console]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.unbeatable-bot :as hard-bot]
            [tic-tac-toe.medium-bot :as medium-bot]
            [tic-tac-toe.easy-bot :as easy-bot]))

(defn- play [io player-1 player-2]
  (loop [board (board/->board [])
         [player next-player] [player-1 player-2]]
    (let [results (board/game-results board)]
      (if (:game-over results)
        (ui/show-results io board)
        (recur (board/mark-square board (player/next-move player board) (player/token player))
               [next-player player])))))

(defn -main [& _]
  (let [io (console/->ConsoleIO)
        token-1 \X
        token-2 \O]
    (ui/show-title io)
    (ui/show-instructions io)
    (if (= :player-vs-player (ui/request-game-mode io))
      (play io (human/->Human token-1 io) (human/->Human token-2 io))
      (let [difficulty (ui/request-difficulty io)]
        (cond
          (= difficulty :easy)
            (play io (human/->Human token-1 io) (easy-bot/->EasyBot token-2))
          (= difficulty :medium)
            (play io (human/->Human token-1 io) (medium-bot/->MediumBot token-2 token-1))
          :else
            (play io (human/->Human token-1 io) (hard-bot/->UnbeatableBot token-2 token-1)))))))
