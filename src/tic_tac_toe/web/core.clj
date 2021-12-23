(ns tic-tac-toe.web.core
  (:require [http-server.core :as h]
            [tic-tac-toe.web.routes.home :as home]
            [tic-tac-toe.game-board :as b]))

(defn page [body]
  (fn [_] (h/create-response 200 body {"Content-Type" "text/html"})))

(def routes
  {:/ {:get (page (home/render (b/->board [])))}})

(defn -main []
  (h/listen (h/create-server 8080 routes)))
