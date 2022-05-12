(ns ttt-data.core)

(defn- type-of [m & _] (:type m))
(defmulti save-game type-of)
(defmulti last-saved-game type-of)
(defmulti find-all-games type-of)
(defmulti disconnect type-of)
(defmulti incomplete-games type-of)
