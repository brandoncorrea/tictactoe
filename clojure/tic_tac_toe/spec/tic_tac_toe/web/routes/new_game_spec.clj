(ns tic-tac-toe.web.routes.new-game-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.routes.new-game :refer :all]
            [ttt-core.game-board :as g]
            [ttt-core.player.human :as human]
            [ttt-core.player.player-dispatcher :as dispatcher]
            [ttt-data.core :as data]
            [ttt-data.datomic-db :as d]
            [tic-tac-toe.util.assert-ttt :as a]
            [http-server.core :as h]))

(def test-uri "datomic:mem://ttt-test-db")

(describe "new game"
  (for [size [3 4]
        mode [:player-vs-player :player-vs-computer]
        difficulty [:easy :medium :hard]]
    (it (format "creates %s %sx%s game as %s" difficulty size size mode)
      (let [db (d/rebuild-database test-uri)
            game {:board         (g/->board [] size)
                  :next-player   (human/->human \X)
                  :second-player (dispatcher/->opponent mode difficulty size \X \O)}]
        (a/responses-should=
          (h/create-response 303 {"Location" "/"})
          (render db {:body [(str "mode=" (subs (str mode) 1) "&size=" size "&difficulty=" (subs (str difficulty) 1) "&new-game=")]}))
        (should= game (dissoc (data/last-saved-game db) :id :ts))))))
