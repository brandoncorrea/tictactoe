(ns tic-tac-toe.gui.state-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.state :refer :all]
            [tic-tac-toe.game-board :as b]))

(describe "state"
  (for [page [:new-game :game-over]]
    (it (format "navigates to %s" page)
      (should= {:page page} (navigate {} page))))

  (it "holds reference to game id"
    (should= 3 (game-id {:game {:id 3}}))
    (should= 4 (game-id {:game {:id 4}})))
  (it "holds reference to board"
    (should= "Dummy Board" (board {:game {:board "Dummy Board"}}))
    (should= :board-placeholder (board {:game {:board :board-placeholder}})))
  (it "holds reference to current player"
    (should= :player1 (player {:game {:next-player :player1}}))
    (should= :player2 (player {:game {:next-player :player2}})))
  (it "holds reference to next player"
    (should= :next-player1 (next-player {:game {:second-player :next-player1}}))
    (should= :next-player2 (next-player {:game {:second-player :next-player2}})))
  (it "holds reference to the current player's token"
    (should= :some-token (player-token {:game {:next-player {:token :some-token}}}))
    (should= \X (player-token {:game {:next-player {:token \X}}})))
  (it "holds reference to current game's 'game over' status"
    (should= true (game-over? {:game {:board (b/->board (range))}}))
    (should= false (game-over? {:game {:board (b/->board [])}})))
  (it "holds reference to current game's draw status"
    (should= true (draw? {:game {:board (b/->board (range))}}))
    (should= false (draw? {:game {:board (b/->board [])}})))
  (it "holds reference to the current game's winner result"
    (should= nil (winner {:game {:board (b/->board (range))}}))
    (should= \X (winner {:game {:board (b/->board (repeat \X))}}))))

(describe "new game"
  (for [size [3 4]]
    (it (format "can set game size to %d" size)
      (should= {:new-game {:size size}} (set-game-size size {}))))
  (for [mode [:player-vs-player :player-vs-computer]]
    (it (format "can set game mode to %s" mode)
      (should= {:new-game {:mode mode}} (set-game-mode mode {}))))
  (for [difficulty [:easy :medium :hard]]
    (it (format "can set game difficulty to %s" difficulty)
      (should= {:new-game {:difficulty difficulty}} (set-game-difficulty difficulty {}))))

  (it "is player vs player"
    (should= true (player-vs-player? {:new-game {:mode :player-vs-player}})))
  (it "is not player vs player when set to player vs computer"
    (should= false (player-vs-player? {:new-game {:mode :player-vs-computer}})))
  (it "is player vs computer"
    (should= true (player-vs-computer? {:new-game {:mode :player-vs-computer}})))
  (it "is not player vs computer when set to player vs player"
    (should= false (player-vs-computer? {:new-game {:mode :player-vs-player}}))))

(describe "active game"
  (it "is human player's turn"
    (should= true (human-turn? {:game {:next-player {:type :human}}})))
  (it "is a non-human player's turn"
    (should= false (human-turn? {:game {:next-player {:type :unbeatable}}}))))
