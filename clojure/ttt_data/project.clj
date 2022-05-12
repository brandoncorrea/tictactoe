(defproject org.clojars.brandoncorrea/ttt-data "0.1.0-SNAPSHOT"
  :description "Tic Tac Toe datomic interface"
  :url "https://github.com/brandoncorrea/ttt-data"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ttt-data.core
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojars.brandoncorrea/ttt-core "0.1.0-SNAPSHOT"]
                 [com.datomic/datomic-free "0.9.5697"]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"])
