(ns ttt-core.player.human-spec
  (:require [speclj.core :refer :all]
            [ttt-core.player.human :refer :all]))

(describe "->human"
  (it "Human player holds a reference to their token"
    (should= \X (:token (->human \X)))
    (should= \O (:token (->human \O)))
    (should= 321 (:token (->human 321)))))
