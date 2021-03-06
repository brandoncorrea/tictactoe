(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main tic-tac-toe.core
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojars.brandoncorrea/ttt-core "0.1.0-SNAPSHOT"]
                 [org.clojars.brandoncorrea/ttt-data "0.1.0-SNAPSHOT"]
                 [com.datomic/datomic-free "0.9.5697"]
                 [quil "3.1.0"]
                 [hiccup "1.0.5"]
                 [ring/ring-codec "1.1.3"]]
  :resource-paths ["src/http_server/HttpServer.jar"]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"]]
  :test-paths ["spec"])
