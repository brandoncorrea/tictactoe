(ns tic-tac-toe.core
  (:use [tic-tac-toe.game-board]
        [tic-tac-toe.user-interface]
        [tic-tac-toe.player]
        [tic-tac-toe.console-io]
        [tic-tac-toe.human]
        [tic-tac-toe.unbeatable-bot]
        [tic-tac-toe.easy-bot]))

(defn- play [io player-1 player-2]
  (loop [board (->board [])
         [player next-player] [player-1 player-2]]
    (let [results (game-results board)]
      (if (:game-over results)
        (show-results io board)
        (recur (mark-square board (next-move player board) (token player))
               [next-player player])))))

(defn -main [& _]
  (let [io (->ConsoleIO)
        token-1 \X
        token-2 \O]
    (show-title io)
    (show-instructions io)
    (cond
      (= :player-vs-player (request-game-mode io))
        (play io (->Human token-1 io) (->Human token-2 io))
      (= :easy (request-difficulty io))
        (play io (->Human token-1 io) (->EasyBot token-2))
      :else
        (play io (->Human token-1 io) (->UnbeatableBot token-2 token-1)))))
