(ns ttt-core.player.player-dispatcher
  (:require [ttt-core.player.random-ai :as random-ai]
            [ttt-core.player.unbeatable-ai :as unbeatable-ai]
            [ttt-core.player.medium-bot :as medium-ai]
            [ttt-core.player.advanced-blocking-ai :as advanced-ai]
            [ttt-core.player.randomly-blocking-ai :as blocking-ai]
            [ttt-core.player.human :as human]))

(defn ->bot
  ([difficulty token-1 token-2 size]
   (->bot difficulty token-1 token-2 size 2))
  ([difficulty token-1 token-2 size dimensions]
   (cond
     (= difficulty :easy) (random-ai/->random-ai token-2)
     (and (< dimensions 3) (< size 4))
     (if (= difficulty :medium)
       (medium-ai/->medium-bot token-2 token-1)
       (unbeatable-ai/->unbeatable-ai token-2 token-1))
     :else
     (if (= difficulty :medium)
       (blocking-ai/->randomly-blocking-ai token-2 token-1)
       (advanced-ai/->advanced-blocking-ai token-2 token-1)))))

(defn ->opponent [game-mode difficulty size token-1 token-2]
  (if (= :player-vs-player game-mode)
    (human/->human token-2)
    (->bot difficulty token-1 token-2 size)))
