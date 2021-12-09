(ns tic-tac-toe.gui.state
  (:require [tic-tac-toe.game-board :as g]
            [tic-tac-toe.player.human :as h]
            [tic-tac-toe.player.player-dispatcher :as dispatcher]
            [tic-tac-toe.data.data :as data]))

(defn game-id [state] (get-in state [:play :id]))
(defn board [state] (get-in state [:play :board]))
(defn player [state] (get-in state [:play :player]))
(defn next-player [state] (get-in state [:play :next-player]))
(defn player-token [state] (:token (player state)))

(defn game-over? [state] (get-in state [:play :results :game-over]))
(defn draw? [state] (get-in state [:play :results :draw]))
(defn winner [state] (get-in state [:play :results :winner]))

(defn- set-new-game [key val state] (assoc-in state [:new-game key] val))
(def set-game-size (partial set-new-game :size))
(def set-game-mode (partial set-new-game :game-mode))
(def set-game-difficulty (partial set-new-game :difficulty))

(defn game-size [state] (get-in state [:new-game :size]))
(defn game-mode [state] (get-in state [:new-game :game-mode]))
(defn difficulty [state] (get-in state [:new-game :difficulty]))
(defn player-vs-computer? [state]
  (= :player-vs-computer (game-mode state)))
(defn player-vs-player? [state]
  (= :player-vs-player (game-mode state)))
(defn human-turn? [state]
  (= :human (:type (player state))))

(defn- ->play [{:keys [id board next-player second-player]}]
  {:id id
   :board board
   :player next-player
   :next-player second-player})

(defn save-game [{db :db :as state}]
  (if-let [id (game-id state)]
    (data/save-game db (board state) (player state) (next-player state) id)
    (data/save-game db (board state) (player state) (next-player state)))
    (assoc state :play (->play (data/last-saved-game db))))

(defn- build-play [state]
  {:board       (g/->board [] (game-size state))
   :player      (h/->human \X)
   :next-player (dispatcher/->opponent
                  (game-mode state)
                  (difficulty state)
                  (game-size state)
                  \X \O)})

(defn build-new-game [state]
  (save-game (assoc (dissoc state :new-game) :play (build-play state))))

(defn move-current-player [cell {play :play :as state}]
  (if (g/valid-move? (board state) cell)
    (assoc state :play
                 (assoc play :player (next-player state)
                             :next-player (player state)
                             :board (g/mark-square (board state) cell (player-token state))))
    state))

(defn can-resume? [game]
  (and game (-> game :board g/game-results :game-over not)))

(defn load-last-saved-game [{db :db :as state}]
  (let [game (data/last-saved-game db)]
    (if (can-resume? game)
      (assoc state :page :load-game
                   :play (->play game))
      state)))

(defn refresh-game-results [state]
  (->> (board state)
       g/game-results
       (assoc-in state [:play :results])))
