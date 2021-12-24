(ns tic-tac-toe.game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.game :refer :all]
            [tic-tac-toe.game-board :as b]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]))

(describe "game"
  (it "initializes with board, player 1, and player 2"
    (should= {:board         (b/->board [])
              :next-player   (human/->human \X)
              :second-player (human/->human \O)}
             (create-game)))

  (for [size [3 4 5]]
    (it (format "intializes with %sx%s board" size size)
      (should= {:board         (b/->board [] size)
                :next-player   (human/->human \X)
                :second-player (human/->human \O)}
               (create-game {:size size}))))

  (it "initializes with specified tokens"
    (should= {:board         (b/->board [])
              :next-player   (human/->human \A)
              :second-player (human/->human \B)}
             (create-game {:token-1 \A :token-2 \B})))

  (for [difficulty [:easy :medium :hard]
        [token-1 token-2] [[\X \O] [\A \B]]
        size [3 4]]
    (it (format "initializes %sx%s with %s difficulty and tokens %s and %s" size size difficulty token-1 token-2)
      (should= {:board         (b/->board [] size)
                :next-player   (human/->human token-1)
                :second-player (dispatcher/->bot difficulty token-1 token-2 size)}
               (create-game {:difficulty difficulty :token-1 token-1 :token-2 token-2 :size size}))))

  (it "initializes as player vs player flag when difficulty is set"
    (should= {:board         (b/->board [])
              :next-player   (human/->human \X)
              :second-player (human/->human \O)}
             (create-game {:mode :player-vs-player :difficulty :hard})))

  (it "initializes token-2 to X when token-1 is O"
    (should= {:board         (b/->board [])
              :next-player   (human/->human \O)
              :second-player (human/->human \X)}
             (create-game {:token-1 \O})))

  (for [key [:token-1 :token-2]
        [token-1 token-2] [[\X \O] [\O \X]]]
    (it (format "initializes opponent to %s when %s is %s" token-2 key token-1)
      (should= {:board         (b/->board [])
                :next-player   (human/->human (if (= key :token-1) token-1 token-2))
                :second-player (human/->human (if (= key :token-2) token-1 token-2))}
               (create-game {key token-1}))))

  (for [[token-1 token-2] [[\X \O] [\O \X]]]
    (it (format "initializes player 1 to %s and player 2 to %s when both tokens are %s" token-1 token-2 token-1)
      (should= {:board         (b/->board [])
                :next-player   (human/->human token-1)
                :second-player (human/->human token-2)}
               (create-game {:token-1 token-1 :token-2 token-1})))))

(describe "move"
  (for [cell [[0 0] [1 1]]]
    (it (format "places the current player's token in cell %s" cell)
      (let [{:keys [board next-player second-player] :as game} (create-game)]
        (should= {:board         (assoc board cell (:token next-player))
                  :next-player   second-player
                  :second-player next-player}
                 (move game cell)))))

  (it "second player after first player moves"
    (let [{:keys [board next-player second-player] :as game} (move (create-game) [0 0])]
      (should= {:board (assoc board [1 0] (:token next-player))
                :next-player second-player
                :second-player next-player}
               (move game [1 0]))))

  (it "results in identity if the cell is already occupied"
    (let [game (assoc-in (create-game) [:board [0 0]] \O)]
      (should= game (move game [0 0]))))

  (it "retains other keys associated with game map"
    (let [game (assoc (create-game) :extra-key :value)]
      (should= :value (:extra-key (move game [0 0]))))))
