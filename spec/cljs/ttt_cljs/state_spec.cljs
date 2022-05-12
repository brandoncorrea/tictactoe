(ns ttt-cljs.state-spec
  (:require-macros [speclj.core :refer [describe it should=]])
  (:require [speclj.core]
            [ttt-cljs.state :as s]
            [ttt-core.game :as g]))

(describe "state-init"
  (it "contains default settings"
    (let [defaults {:size       3
                    :mode       :player-vs-computer
                    :difficulty :medium}]
      (should= {:new-game defaults
                :game     (g/create-game defaults)}
               s/state-init))))

(describe "state"
  (it "Initializes atom with state-init"
    (should= @s/state s/state-init)))

(describe "accessors"
  (for [[title ks read] [["game mode" [:new-game :mode] s/game-mode]
                         ["size" [:new-game :size] s/size]
                         ["difficulty" [:new-game :difficulty] s/difficulty]
                         ["game board" [:game :board] s/board]]]
    (it (str "retrieves " title)
      (should= (get-in @s/state ks) (read)))))

(describe "setters"
  (for [[title set read [v1 v2]] [["game mode" s/set-game-mode s/game-mode [:test-1 :dummy]]
                                  ["size" s/set-size s/size [5 4]]
                                  ["difficulty" s/set-difficulty s/difficulty [:hard :really-hard]]]]
    (it (str "updates " title)
      (set v1)
      (should= v1 (read))
      (set v2)
      (should= v2 (read))
      (reset! s/state s/state-init))))

(describe "updaters"
  (for [[name setter reader updater value]
        [["game-mode" s/set-game-mode s/game-mode s/set-pvp :player-vs-player]
         ["game-mode" s/set-game-mode s/game-mode s/set-pvc :player-vs-computer]
         ["size" s/set-size s/size s/set-3x3 3]
         ["size" s/set-size s/size s/set-4x4 4]
         ["difficulty" s/set-difficulty s/difficulty s/set-easy :easy]
         ["difficulty" s/set-difficulty s/difficulty s/set-medium :medium]
         ["difficulty" s/set-difficulty s/difficulty s/set-hard :hard]]]
    (it (str "sets " name " to " value)
      (setter :nothin)
      (should= :nothin (reader))
      (updater)
      (should= value (reader))
      (reset! s/state s/state-init))))

(describe "new-game"
  (for [size [3 4]
        mode [:player-vs-player :player-vs-computer]
        difficulty [:easy :medium :hard]]
    (it (str "creates new game with size " size ", mode " mode ", and difficulty " difficulty)
      (s/set-game-mode mode)
      (s/set-size size)
      (s/set-difficulty difficulty)
      (s/new-game)
      (should= (g/create-game {:size size :mode mode :difficulty difficulty}) (:game @s/state))
      (reset! s/state s/state-init))))

(describe "move"
  (for [cell [[0 0] [1 2]]]
    (it (str "moves current player to " cell)
      (let [game (:game @s/state)]
        (s/move cell)
        (should= (:game @s/state) (g/move game cell))))))