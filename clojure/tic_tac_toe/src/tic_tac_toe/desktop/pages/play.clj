(ns tic-tac-toe.desktop.pages.play
  (:require [tic-tac-toe.desktop.state :as s]
            [tic-tac-toe.desktop.pages.page :as page]
            [tic-tac-toe.desktop.components.component :as c]
            [tic-tac-toe.desktop.components.header :as h]
            [tic-tac-toe.desktop.components.ttt-board :as t]
            [ttt-core.player.player :as p]))

(defn toggle-game-over [state]
  (if (s/game-over? state)
    (s/navigate state :game-over)
    state))

(defn- draw-header [state]
  (-> (s/player-token state)
      (str "'s turn!")
      h/->header
      (c/draw state)))

(defmethod page/render-page :play [state]
  (draw-header state)
  (c/draw t/board state))

(defn- move-bot [state]
  (s/move-current-player (p/next-move (s/player state) (s/board state)) state))

(defn- update-bot [state]
  (if (or (s/human-turn? state) (s/game-over? state))
    state
    (move-bot state)))

(defmethod page/update-page :play [state]
  (->> (c/update-state t/board state)
       update-bot
       s/save
       toggle-game-over))
