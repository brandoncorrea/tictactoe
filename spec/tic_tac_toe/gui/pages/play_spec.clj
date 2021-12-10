(ns tic-tac-toe.gui.pages.play-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.pages.play :refer :all]))

(def game-over-result {:play {:results {:game-over true}}})
(def game-not-over-result {:play {:results {:game-over false}}})

(describe "play"
  (it "does not navigate to game over when results do not reflect a game over"
    (should= game-not-over-result (toggle-game-over game-not-over-result)))
  (it "navigates to game over when results reflect a game over"
    (should= (assoc game-over-result :page :game-over)
             (toggle-game-over game-over-result))))
