(ns tic-tac-toe.gui.pages.play
  (:require [tic-tac-toe.gui.state :as s]
            [tic-tac-toe.gui.pages.page :as page]
            [tic-tac-toe.gui.components.component :as c]
            [tic-tac-toe.gui.components.header :as h]
            [tic-tac-toe.gui.components.ttt-board :as t]
            [tic-tac-toe.player.player :as p]))

(defn- draw-header [state]
  (-> (s/player-token state)
      (str "'s turn!")
      h/->header
      (c/draw state)))

(defmethod page/render-page :play [state]
  (draw-header state)
  (c/draw t/board state))

(defn move-bot [state]
  (s/move-current-player (p/next-move (s/player state) (s/board state)) state))

(defn- update-bot [state]
  (if (or (s/human-turn? state) (s/game-over? state))
    state
    (move-bot state)))

(defmethod page/update-page :play [state]
  (->> (c/update-state t/board state)
       s/refresh-game-results
       update-bot
       s/refresh-game-results
       s/save-game))
