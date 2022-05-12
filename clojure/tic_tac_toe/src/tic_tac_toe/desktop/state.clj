(ns tic-tac-toe.desktop.state
  (:require [ttt-core.game :as g]
            [ttt-data.core :as data]))

(defn navigate [state page] (assoc state :page page))

(defn game-id [state] (get-in state [:game :id]))
(defn board [state] (get-in state [:game :board]))
(defn player [state] (get-in state [:game :next-player]))
(defn next-player [state] (get-in state [:game :second-player]))
(defn player-token [state] (:token (player state)))

(defn game-over? [{game :game}] (g/game-over? game))
(defn draw? [{game :game}] (g/draw? game))
(defn winner [{game :game}] (g/winner game))

(defn- set-new-game [key val state] (assoc-in state [:new-game key] val))
(def set-game-size (partial set-new-game :size))
(def set-game-mode (partial set-new-game :mode))
(def set-game-difficulty (partial set-new-game :difficulty))

(defn game-size [state] (get-in state [:new-game :size]))
(defn game-mode [state] (get-in state [:new-game :mode]))
(defn difficulty [state] (get-in state [:new-game :difficulty]))
(defn player-vs-computer? [state]
  (= :player-vs-computer (game-mode state)))
(defn player-vs-player? [state]
  (= :player-vs-player (game-mode state)))
(defn human-turn? [state]
  (= :human (:type (player state))))

(defn save [{db :db game :game :as state}]
  (data/save-game db game)
  state)

(defn- create-game [{new-game :new-game}]
  (g/create-game new-game))

(defn build-new-game [state]
  (save (assoc (dissoc state :new-game) :game (create-game state))))

(defn move-current-player [cell {game :game :as state}]
  (assoc state :game (g/move game cell)))

(defn load-last-saved-game [{db :db :as state}]
  (let [game (data/last-saved-game db)]
    (if (g/can-resume? game)
      (assoc state :page :load-game
                   :game game)
      state)))
