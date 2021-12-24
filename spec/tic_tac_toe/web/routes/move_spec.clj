(ns tic-tac-toe.web.routes.move-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.routes.move :refer :all]
            [tic-tac-toe.util.datomic-mem :as dm]
            [tic-tac-toe.web.pages.home :as home]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.game-board :as g]
            [tic-tac-toe.player.human :as human]
            [tic-tac-toe.util.assert-ttt :as a]))

(defn url-cell [[x y]] (str "cell=%5B" x "+" y "%5D"))

(describe "move"
  (for [cell [[1 2] [0 0]]
        [token-1 token-2] [[\X \O] [\O \X]]]
    (it (format "places token %s in cell %s" token-1 cell)
      (let [db (dm/recreate-db)
            _ (data/save-game db (g/->board []) (human/->human token-1) (human/->human token-2))
            game (assoc-in (data/last-saved-game db) [:board cell] token-1)
            game (assoc game :next-player (:second-player game)
                             :second-player (:next-player game))]
        (a/responses-should= (home/render game)
                             (render db {:body [(url-cell cell)]}))
        (should= (dissoc game :ts) (dissoc (data/last-saved-game db) :ts))))))
