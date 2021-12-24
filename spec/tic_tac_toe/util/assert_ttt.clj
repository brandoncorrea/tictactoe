(ns tic-tac-toe.util.assert-ttt
  (:require [speclj.core :refer :all]))

(defn responses-should= [res1 res2]
  (should= (:status res1) (:status res2))
  (should= (:headers res1) (:headers res2))
  (should= (map identity (:body res1)) (map identity (:body res2))))