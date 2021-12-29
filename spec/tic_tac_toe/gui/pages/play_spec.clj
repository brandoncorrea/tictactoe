(ns tic-tac-toe.gui.pages.play-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game-board :as b]
            [tic-tac-toe.gui.pages.play :refer :all]))

(def game-over-result {:game {:board (b/->board (range))}})
(def game-not-over-result {:game {:board (b/->board [])}})

(describe "play"
  (it "does not navigate to game over when results do not reflect a game over"
    (should= game-not-over-result (toggle-game-over game-not-over-result)))
  (it "navigates to game over when results reflect a game over"
    (should= (assoc game-over-result :page :game-over)
             (toggle-game-over game-over-result))))
