(ns tic-tac-toe.game
  (:require [tic-tac-toe.game-board :as b]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]
            [tic-tac-toe.player.player :as p]))

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

(defn- swap-players [{:keys [next-player second-player] :as game}]
  (assoc game :next-player second-player :second-player next-player))

(defn- cell-occupied? [game cell] (get-in game [:board cell]))

(defn- human-opponent? [game]
  (-> game :second-player :type (= :human)))

(def game-over? (comp :game-over b/game-results :board))

(defn- move-player [game {token :token} cell]
  (assoc-in game [:board cell] token))

(defn- move-vs-human [{player :next-player :as game} cell]
  (swap-players (move-player game player cell)))

(defn- move-vs-bot [{:keys [next-player second-player] :as game} cell]
  (let [{board :board :as game} (move-player game next-player cell)]
    (if (game-over? game)
      game
      (move-player game second-player (p/next-move second-player board)))))

(defn move [game cell]
  (cond
    (or (cell-occupied? game cell) (game-over? game)) game
    (human-opponent? game) (move-vs-human game cell)
    :else (move-vs-bot game cell)))
