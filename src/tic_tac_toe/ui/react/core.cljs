(ns tic-tac-toe.ui.react.core
  (:require react-dom
            [tic-tac-toe.game :as g]))

(println "Hello world!")

(.render js/ReactDOM
  (.createElement js/React "h2" nil "Hello, React!")
  (.getElementById js/document "app"))

(println (g/create-game))
