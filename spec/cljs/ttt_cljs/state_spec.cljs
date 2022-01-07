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
  (it "retrieves game mode"
    (should= (get-in @s/state [:new-game :mode]) (s/game-mode)))
  (it "retrieves game size"
    (should= (get-in @s/state [:new-game :size]) (s/size)))
  (it "retrieves game difficulty"
    (should= (get-in @s/state [:new-game :difficulty]) (s/difficulty)))
  (it "retrieves game board"
    (should= (get-in @s/state [:game :board]) (s/board))))

(describe "setters"
  (it "updates game mode"
    (s/set-game-mode :test-1)
    (should= :test-1 (s/game-mode))
    (s/set-game-mode :another-test)
    (should= :another-test (s/game-mode))
    (reset! s/state s/state-init))
  (it "updates game size"
    (s/set-size 5)
    (should= 5 (s/size))
    (s/set-size 4)
    (should= 4 (s/size))
    (reset! s/state s/state-init))
  (it "updates difficulty"
    (s/set-difficulty :hard)
    (should= :hard (s/difficulty))
    (s/set-difficulty :really-hard)
    (should= :really-hard (s/difficulty))
    (reset! s/state s/state-init)))

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
      (should= (g/create-game {:size size :mode mode :difficulty difficulty}) (:game @s/state)))))
