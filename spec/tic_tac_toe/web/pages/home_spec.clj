(ns tic-tac-toe.web.pages.home-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.pages.home :refer :all]))

(describe "home-page"
  (it "renders HTML content"
    (should= "<h1>Tic Tac Toe</h1>" body))
  (it "results in status code 200"
    (should= 200 (:status (home-page nil))))
  (it "contains Content-Type text/html header"
    (should= "text/html" (get (:headers (home-page nil)) "Content-Type"))))
