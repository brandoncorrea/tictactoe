(ns tic-tac-toe.web.routes.new-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.routes.new-game :refer :all]
            [tic-tac-toe.web.pages.home :as home]
            [tic-tac-toe.game-board :as g]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.util.datomic-mem :as dm]
            [tic-tac-toe.util.assert-ttt :as a]))

(describe "new game"
  (for [size [3 4]
        mode [:player-vs-player :player-vs-computer]
        difficulty [:easy :medium :hard]]
    (it (format "creates %s %sx%s game as %s" difficulty size size mode)
      (let [db (dm/recreate-db)
            game {:board         (g/->board [] size)
                  :next-player   (human/->human \X)
                  :second-player (dispatcher/->opponent mode difficulty size \X \O)}]
        (a/responses-should=
          (home/render game)
          (render db {:body [(str "mode=" (subs (str mode) 1) "&size=" size "&difficulty=" (subs (str difficulty) 1) "&new-game=")]}))
        (should= game (dissoc (data/last-saved-game db) :id :ts))))))
