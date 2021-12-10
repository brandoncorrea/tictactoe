(ns tic-tac-toe.gui.router
  (:require [tic-tac-toe.gui.state :as s]))

(defn navigate [state page] (assoc state :page page))

(defn create-game? [{page :page :as state}]
  (boolean (and (= :new-game page)
                (s/game-size state)
                (or (s/player-vs-player? state)
                    (and (s/difficulty state)
                         (s/game-mode state))))))

(defn show-game-over? [{page :page :as state}]
  (or (= :game-over page)
      (and (= :play page)
           (s/game-over? state))))

(defn route [state]
  (if (show-game-over? state)
    (navigate state :game-over)
    state))
