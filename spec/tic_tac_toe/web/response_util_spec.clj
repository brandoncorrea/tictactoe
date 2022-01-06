(ns tic-tac-toe.web.response-util-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.web.response-util :refer :all]))

(def empty-page (render-page ""))

(describe "page"
  (it "results in status code 200"
    (should= 200 (:status empty-page)))
  (it "contains Content-Type text/html header"
    (should= "text/html" (get (:headers empty-page) "Content-Type")))
  (for [content ["Hello, world!" "Chicken noodle soup"]]
    (it (format "returns %s as bytes in body" content)
      (should= (map identity (.getBytes content))
               (map identity (:body (render-page content)))))))

