(ns tic-tac-toe.ui.user-interface)

(defn- type-of [m & _] (:type m))
(defmulti show-title type-of)
(defmulti show-instructions type-of)
(defmulti show-results type-of)
(defmulti request-game-mode type-of)
(defmulti request-board-size type-of)
(defmulti request-board-dimensions type-of)
(defmulti request-difficulty type-of)
(defmulti request-move type-of)