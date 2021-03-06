(ns ttt-core.player.player-dispatcher-spec
  (:require [speclj.core :refer :all]
            [ttt-core.player.player-dispatcher :as d]
            [ttt-core.player.unbeatable-ai :refer [->unbeatable-ai]]
            [ttt-core.player.random-ai :refer [->random-ai]]
            [ttt-core.player.medium-bot :refer [->medium-bot]]
            [ttt-core.player.randomly-blocking-ai :refer [->randomly-blocking-ai]]
            [ttt-core.player.advanced-blocking-ai :refer [->advanced-blocking-ai]]))

(defn test-ai [ai-desc difficulty options ->ai]
  (for [[size dim] options]
    (it (format "%dD %dx%d on %s difficulty results in %s" dim size size difficulty ai-desc)
      (should= (->ai \X \O) (d/->bot difficulty \O \X size dim)))))

(describe "->bot"
  (it "Default bot is Unbeatable AI"
    (should= (->unbeatable-ai \X \O) (d/->bot nil \O \X 3 2)))
  (test-ai "Random AI" :easy [[3 2] [3 3] [5 5] [1 1] [100 100]] (fn [a _] (->random-ai a)))
  (test-ai "Medium Bot" :medium [[3 2]] ->medium-bot)
  (test-ai "Unbeatable AI" :hard [[3 2] [2 2] [1 2]] ->unbeatable-ai)
  (test-ai "Advanced Blocking AI" :hard [[3 3] [4 2] [2 3]] ->advanced-blocking-ai)
  (test-ai "Randomly Blocking AI" :medium [[3 3] [4 2] [2 3]] ->randomly-blocking-ai))
