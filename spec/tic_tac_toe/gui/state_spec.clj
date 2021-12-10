(ns tic-tac-toe.gui.state-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.gui.state :refer :all]))

(describe "state"
  (for [page [:new-game :game-over]]
    (it (format "navigates to %s" page)
      (should= {:page page} (navigate {} page))))

  (it "holds reference to game id"
    (should= 3 (game-id {:play {:id 3}}))
    (should= 4 (game-id {:play {:id 4}})))
  (it "holds reference to board"
    (should= "Dummy Board" (board {:play {:board "Dummy Board"}}))
    (should= :board-placeholder (board {:play {:board :board-placeholder}})))
  (it "holds reference to current player"
    (should= :player1 (player {:play {:player :player1}}))
    (should= :player2 (player {:play {:player :player2}})))
  (it "holds reference to next player"
    (should= :next-player1 (next-player {:play {:next-player :next-player1}}))
    (should= :next-player2 (next-player {:play {:next-player :next-player2}})))
  (it "holds reference to the current player's token"
    (should= :some-token (player-token {:play {:player {:token :some-token}}}))
    (should= \X (player-token {:play {:player {:token \X}}})))
  (it "holds reference to current game's 'game over' status"
    (should= true (game-over? {:play {:results {:game-over true}}}))
    (should= false (game-over? {:play {:results {:game-over false}}})))
  (it "holds reference to current game's draw status"
    (should= true (draw? {:play {:results {:draw true}}}))
    (should= false (draw? {:play {:results {:draw false}}})))
  (it "holds reference to the current game's winner result"
    (should= nil (winner {:play {:results {:winner nil}}}))
    (should= \X (winner {:play {:results {:winner \X}}}))))

(describe "new game"
  (for [size [3 4]]
    (it (format "can set game size to %d" size)
      (should= {:new-game {:size size}} (set-game-size size {}))))
  (for [mode [:player-vs-player :player-vs-computer]]
    (it (format "can set game mode to %s" mode)
      (should= {:new-game {:game-mode mode}} (set-game-mode mode {}))))
  (for [difficulty [:easy :medium :hard]]
    (it (format "can set game difficulty to %s" difficulty)
      (should= {:new-game {:difficulty difficulty}} (set-game-difficulty difficulty {}))))

  (it "is player vs player"
    (should= true (player-vs-player? {:new-game {:game-mode :player-vs-player}})))
  (it "is not player vs player when set to player vs computer"
    (should= false (player-vs-player? {:new-game {:game-mode :player-vs-computer}})))
  (it "is player vs computer"
    (should= true (player-vs-computer? {:new-game {:game-mode :player-vs-computer}})))
  (it "is not player vs computer when set to player vs player"
    (should= false (player-vs-computer? {:new-game {:game-mode :player-vs-player}}))))

(describe "active game"
  (it "is human player's turn"
    (should= true (human-turn? {:play {:player {:type :human}}})))
  (it "is a non-human player's turn"
    (should= false (human-turn? {:play {:player {:type :unbeatable}}}))))
