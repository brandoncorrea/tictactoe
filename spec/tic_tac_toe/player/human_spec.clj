(ns tic-tac-toe.player.human-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.player.human :refer :all]))

(describe "->human"
  (it "Human player holds a reference to their token"
    (should= \X (:token (->human \X "some io")))
    (should= \O (:token (->human \O nil)))
    (should= 321 (:token (->human 321 [999]))))
  (it "Human player holds a reference to a UI"
    (should= "Some IO" (:ui (->human \X "Some IO")))
    (should= 924 (:ui (->human \O 924)))))
