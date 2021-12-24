(ns tic-tac-toe.game
  (:require [tic-tac-toe.game-board :as b]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]))

(defn- create-opponent [mode difficulty token-1 token-2 size]
  (if (or (= mode :player-vs-computer)
          (and difficulty (not= mode :player-vs-player)))
    (dispatcher/->bot difficulty token-1 token-2 size)
    (human/->human token-2)))

(defn- get-token-1 [token-1 token-2]
  (or token-1
      (and (not= token-2 \X) \X)
      \O))

(defn- get-token-2 [token-1 token-2]
  (or (and (not= token-1 token-2) token-2)
      (and (not= token-1 \O) \O)
      \X))

(defn create-game
  ([] {:board         (b/->board [])
       :next-player   (human/->human \X)
       :second-player (human/->human \O)})
  ([{:keys [size token-1 token-2 difficulty mode]}]
   (let [size (or size 3)
         token-1 (get-token-1 token-1 token-2)
         token-2 (get-token-2 token-1 token-2)]
     {:board         (b/->board [] size)
      :next-player   (human/->human token-1)
      :second-player (create-opponent mode difficulty token-1 token-2 size)})))

(defn move [{:keys [board next-player second-player] :as game} cell]
  (if (get board cell)
    game
    (assoc game :board (assoc board cell (:token next-player))
                :next-player second-player
                :second-player next-player)))
