(ns tic-tac-toe.resources.data)

(defn- type-of [m & _] (:type m))
(defmulti save-game type-of)
(defmulti last-saved-game type-of)
(defmulti find-all-games type-of)
