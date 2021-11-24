(ns tic-tac-toe.core
  (:require [tic-tac-toe.game-board :as board]
            [tic-tac-toe.ui.user-interface :as ui]
            [tic-tac-toe.ui.console-io :as console]
            [tic-tac-toe.player.player :as player]
            [tic-tac-toe.player.random-ai :as random-ai]
            [tic-tac-toe.player.medium-bot :as medium-bot]
            [tic-tac-toe.player.unbeatable-ai :as unbeatable-ai]
            [tic-tac-toe.player.human :as human]))

(defn- play [io player-1 player-2]
  (let [size (ui/request-board-size io)]
    (loop [board (board/->board [] size (ui/request-board-dimensions io size))
           [player next-player] [player-1 player-2]]
      (let [results (board/game-results board)]
        (if (:game-over results)
          (ui/show-results io board)
          (recur (board/mark-square board (player/next-move player board) (:token player))
                 [next-player player]))))))

(defn ->bot [difficulty token-1 token-2]
  (cond
    (= difficulty :easy) (random-ai/->random-ai token-2)
    (= difficulty :medium) (medium-bot/->medium-bot token-2 token-1)
    :else (unbeatable-ai/->unbeatable-ai token-2 token-1)))

(defn- new-game [io token-1 token-2]
  (if (= :player-vs-player (ui/request-game-mode io))
    (play io (human/->human token-1 io) (human/->human token-2 io))
    (play io (human/->human token-1 io) (->bot (ui/request-difficulty io) token-1 token-2))))

(defn -main [& _]
  (let [io (console/->ConsoleIO)]
    (ui/show-title io)
    (ui/show-instructions io)
    (new-game io \X \O)))
