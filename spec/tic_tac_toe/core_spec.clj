(ns tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.core :refer :all]
            [tic-tac-toe.player.player :refer :all]
            [tic-tac-toe.player.random-ai :refer :all]
            [tic-tac-toe.player.medium-bot :refer :all]
            [tic-tac-toe.player.unbeatable-ai :refer :all]
            [tic-tac-toe.player.randomly-blocking-ai :refer :all]
            [tic-tac-toe.player.advanced-blocking-ai :refer :all]))

(defn test-ai [ai-desc difficulty options ->ai]
  (for [[size dim] options]
    (it (format "%dD %dx%d on %s difficulty results in %s" dim size size difficulty ai-desc)
      (should= (->ai \X \O) (->bot difficulty \O \X size dim)))))

(describe "->bot"
  (it "Default bot is Unbeatable AI"
    (should= (->unbeatable-ai \X \O) (->bot nil \O \X 3 2)))
  (test-ai "Random AI" :easy [[3 2] [3 3] [5 5] [1 1] [100 100]] (fn [a _] (->random-ai a)))
  (test-ai "Medium Bot" :medium [[3 2]] ->medium-bot)
  (test-ai "Unbeatable AI" :hard [[3 2] [2 2] [1 2]] ->unbeatable-ai)
  (test-ai "Advanced Blocking AI" :hard [[3 3] [4 2] [2 3]] ->advanced-blocking-ai)
  (test-ai "Randomly Blocking AI" :medium [[3 3] [4 2] [2 3]] ->randomly-blocking-ai))
