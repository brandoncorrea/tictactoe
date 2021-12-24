(ns tic-tac-toe.util.url
  (:require [ring.util.codec :as r]))

(defn parse-value [v]
  (if (empty? v)
    nil
    (let [v (read-string v)]
      (if (symbol? v)
        (keyword v)
        v))))

(defn decode [s]
  (into {}
        (for [[k v] (vec (r/form-decode s))]
          [(keyword k) (parse-value v)])))
