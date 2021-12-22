(ns tic-tac-toe.web.core
  (:require [http-server.core :as h]
            [tic-tac-toe.web.pages.home :as home]))

(defn page [body]
  (fn [_] (h/create-response 200 body {"Content-Type" "text/html"})))

(def routes
  {:* {:get (page home/body)}})

(defn -main []
  (h/listen (h/create-server 8080 routes)))
