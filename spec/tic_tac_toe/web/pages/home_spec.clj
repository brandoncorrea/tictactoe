(ns tic-tac-toe.web.pages.home-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.pages.home :refer :all]))

(describe "home-page"
  (it "renders HTML content"
    (should= "<h1>Tic Tac Toe</h1>" body)))

