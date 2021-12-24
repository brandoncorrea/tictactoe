(ns tic-tac-toe.web.routes.index
  (:require [tic-tac-toe.web.pages.home :as home]
            [tic-tac-toe.game-board :as g]
            [tic-tac-toe.data.data :as data]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]
            [tic-tac-toe.player.human :as human]))

(defn should-load? [board]
  (= 2 (g/dimensions board)))

(defn- create-new-game [db]
  (data/save-game db (g/->board []) (human/->human \X) (dispatcher/->bot :medium \X \O 3))
  (data/last-saved-game db))

(defn load-board [db]
  (let [game (data/last-saved-game db)]
    (if (should-load? (:board game))
      game
      (create-new-game db))))

(defn render [db _] (home/render (load-board db)))
