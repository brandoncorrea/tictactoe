(ns tic-tac-toe.web.core
  (:require [http-server.core :as h]
            [tic-tac-toe.web.pages.home :refer [home-page]]))

(def routes
  {:* {:get home-page}})

(defn -main []
  (h/listen (h/create-server 8080 routes)))
