(ns tic-tac-toe.ui.web.response-util
  (:require [http-server.core :as h]))

(defn render-page [content]
  (h/create-response 200 content {"Content-Type" "text/html"}))
