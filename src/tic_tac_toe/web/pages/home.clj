(ns tic-tac-toe.web.pages.home
  (:require [hiccup.core :as h]
            [http-server.core :as http]))

(def body
  (h/html [:h1 "Tic Tac Toe"]))

(defn home-page [_]
  (http/create-response 200 body {"Content-Type" "text/html"}))
