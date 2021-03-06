(ns ttt-core.game-spec
  (:require [speclj.core :refer :all]
            [ttt-core.game :refer :all]
            [ttt-core.game-board :as b]
            [ttt-core.player.human :as human]
            [ttt-core.player.player-dispatcher :as dispatcher]
            [ttt-core.player.player :as p]))

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
               (create-game {:token-1 token-1 :token-2 token-1}))))

  (it "cannot resume if game is nil"
    (should= false (can-resume? nil)))
  (it "cannot resume if board is nil"
    (should= false (can-resume? {:board nil})))
  (it "can be resumed if no moves are made"
    (should= true (can-resume? {:board (b/->board [])})))
  (it "can resume if moves are made without winners"
    (should= true (can-resume? {:board (b/->board (range 4))})))
  (it "cannot resume if there is a winner"
    (should= false (can-resume? {:board (b/->board [1 1 1])})))
  (it "cannot resume draw game"
    (should= false (can-resume? {:board (b/->board (range))}))))

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

  (it "prevents further moves if game is over"
    (let [game (assoc (create-game) :board (b/->board [\X \X \X]))]
      (should= game (move game [2 2]))))

  (it "prevents further moves vs AI if game is over"
    (let [game (assoc (create-game {:difficulty :medium}) :board (b/->board [\X \X \X]))]
      (should= game (move game [2 2]))))

  (for [[token-1 token-2] [[\X \O] [\O \X]]
        cell [[0 0] [2 2]]]
    (it (format "AI moves immediately after player %s moves to %s" token-1 cell)
      (let [{next-player-1   :next-player
             second-player-1 :second-player
             board-1         :board :as game-init}
            (create-game {:difficulty :medium :token-1 token-1 :token-2 token-2})
            bot-cell (p/next-move second-player-1 (assoc board-1 cell token-1))
            {:keys [board next-player second-player]}
            (move game-init cell)]
        (should= next-player-1 next-player)
        (should= second-player-1 second-player)
        (should= board (assoc board-1 cell token-1 bot-cell token-2)))))

  (it "players are not modified when AI is opponent"
    (let [game (create-game {:difficulty :medium})
          game (assoc-in game [:next-player :id] 123)
          game (assoc-in game [:second-player :id] 456)
          game (move game [0 0])]
      (should= 123 (get-in game [:next-player :id]))
      (should= 456 (get-in game [:second-player :id]))))

  (it "does not move AI when game is over"
    (let [game (create-game {:difficulty :medium})
          game (assoc game :board (b/->board [\X \X]))
          game (move game [0 2])]
      (should= 3 (count (filter #(not (nil? (second %))) (:board game))))))

  (it "results in identity if the cell is already occupied"
    (let [game (assoc-in (create-game) [:board [0 0]] \O)]
      (should= game (move game [0 0]))))

  (it "retains other keys associated with game when vs AI"
    (let [game (assoc (create-game {:difficulty :easy}) :extra-key :value)]
      (should= :value (:extra-key (move game [0 0])))))

  (it "retains other keys associated with game map"
    (let [game (assoc (create-game) :extra-key :value)]
      (should= :value (:extra-key (move game [0 0]))))))
