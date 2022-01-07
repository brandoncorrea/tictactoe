(ns ttt-cljs.state
  (:require [reagent.core :as r]
            [ttt-core.game :as g]))

(def ^:private default-game
  {:size       3
   :mode       :player-vs-computer
   :difficulty :medium})
(def state-init
  {:new-game default-game
   :game     (g/create-game default-game)})

(defonce state (r/atom state-init))

(defn game-mode [] (get-in @state [:new-game :mode]))
(defn size [] (get-in @state [:new-game :size]))
(defn difficulty [] (get-in @state [:new-game :difficulty]))
(defn board [] (get-in @state [:game :board]))

(defn- set-state [ks v] (swap! state #(assoc-in % ks v)))
(def set-game-mode (partial set-state [:new-game :mode]))
(def set-size (partial set-state [:new-game :size]))
(def set-difficulty (partial set-state [:new-game :difficulty]))

(defn set-pvp [] (set-game-mode :player-vs-player))
(defn set-pvc [] (set-game-mode :player-vs-computer))
(defn set-3x3 [] (set-size 3))
(defn set-4x4 [] (set-size 4))
(defn set-easy [] (set-difficulty :easy))
(defn set-medium [] (set-difficulty :medium))
(defn set-hard [] (set-difficulty :hard))

(defn new-game []
  (swap! state #(assoc % :game (g/create-game (:new-game %)))))

(defn move [cell]
  (swap! state #(assoc % :game (g/move (:game %) cell))))