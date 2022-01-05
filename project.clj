(defproject org.clojars.brandoncorrea/ttt-core "0.1.0-SNAPSHOT"
  :description "Tic Tac Toe core logic"
  :url "https://github.com/brandoncorrea/ttt-core"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-codec "1.1.3"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"])
