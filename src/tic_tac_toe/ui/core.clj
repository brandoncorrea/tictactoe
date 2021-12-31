(ns tic-tac-toe.ui.core)

(def ns-guis
  {"--console" ['tic-tac-toe.ui.console.core '-main]
   "--desktop" ['tic-tac-toe.ui.desktop.core '-main]
   "--web"     ['tic-tac-toe.ui.web.core '-main]})

(defn init [ns-name main-fn args]
  (require (symbol ns-name))
  (let [ns (find-ns ns-name)]
    (apply (ns-resolve ns (symbol main-fn)) args)))

(defn print-err [ui-option]
  (println "Invalid argument:" ui-option)
  (println "Options:")
  (doseq [option (keys ns-guis)]
    (println " " option)))

(defn -main [& args]
  (if-let [[ns-name main-fn] (get ns-guis (first args))]
    (init ns-name main-fn (rest args))
    (print-err (first args))))
