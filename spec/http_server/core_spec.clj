(ns http-server.core-spec
  (:require [speclj.core :refer :all]
            [http-server.core :refer :all]
            [clojure.string :as s])
  (:import [httpServer HttpRequest]))

(describe "http-server"
  (it "creates a new server on port 80"
    (should= 80 (.port (create-server {}))))
  (it "creates a new server with custom port"
    (should= 1234 (.port (create-server 1234 {})))))

(describe "HttpRequest map conversion"
  (for [method ["GET" "POST" "HEAD"]]
    (let [request (HttpRequest. (str method " / HTTP/1.1"))
          expected (keyword (s/lower-case method))]
      (it (format "converts HttpMethod %s to %s" method expected)
        (should= expected (:method (->http-request request))))))

  (for [[route-str route-key] [["/" :/]
                               ["/hello" :hello]
                               ["/goodbye" :goodbye]
                               ["/path/a/b/c" :path:a:b:c]]]
    (it (format "contains parsed URI %s as %s" route-str route-key)
      (let [request (HttpRequest. (str "GET " route-str " HTTP/1.1"))]
        (should= route-key (:uri (->http-request request))))))

  (for [protocol ["HTTP/1.1" "HTTP/1.0" "RandomText"]]
    (it (format "contains plaintext protocol: %s" protocol)
      (let [request (HttpRequest. (str "GET / " protocol))]
        (should= protocol (:protocol (->http-request request))))))

  (for [body ["H" "Hello" "Hello\r\nWorld!\r\nThird line\r\n\r\nlast line"]]
    (it (format "contains byte content of body: %s" body)
      (let [request (HttpRequest. (str "GET / HTTP/1.1\r\n\r\n" body))]
        (should= (s/split body #"\r\n") (:body (->http-request request))))))

  (for [type ["text/plain" "text/html" "application/json"]]
    (it (format "contains Content-Type %s header" type)
      (let [request (HttpRequest. (str "GET / HTTP/1.1\r\nContent-Type: " type))]
        (should= {"Content-Type" type} (:headers (->http-request request)))))))

(describe "http-response"
  (it "can be converted from empty string map into keyword map"
    (should= {} (->http-response {})))
  (it "can be converted from single-item string map into keyword map"
    (should= {:status 200} (->http-response {"status" 200})))
  (it "can be converted from multi-item map into keyword map"
    (should= {:status 200 :headers {"Content-Type" "text/html"}}
             (->http-response {"status" 200 "headers" {"Content-Type" "text/html"}})))

  (it "can be converted from empty keyword map into string map"
    (should= {} (->HttpResponse {})))
  (it "can be converted from single-item keyword map into string map"
    (should= {"status" 200} (->HttpResponse {:status 200})))
  (it "can be converted from multi-item keyword map into string map"
    (should= {"status" 200 "headers" {"Content-Type" "text/html"}}
             (->HttpResponse {:status 200 :headers {"Content-Type" "text/html"}}))))

(def routes
  {:/ {:get (fn [_] (create-response 200))}
   :hello {:get (fn [_] (create-response 201))}})

(describe "router"
  (it "results in 404 when no routes exist"
    (should= 404 (:status (route {:uri :/ :method :get} {}))))
  (it "defaults to * route if requested path is not found"
    (should= 303 (:status (route {:uri :hello :method :get}
                                 {:* {:get (fn [_] (create-response 303))}}))))
  (it "results in 405 when route exists but the requested method is invalid"
    (should= 405 (:status (route {:uri :/ :method :post} routes))))
  (it "results OK result when route exists"
    (should= 200 (:status (route {:uri :/ :method :get} routes))))
  (it "receives response from function associated with route"
    (should= 201 (:status (route {:uri :hello :method :get} routes)))))

(describe "http-response"
  (for [status [200 500]]
    (it (format "contains status code %s" status)
      (should= status (:status (create-response status)))))

  (it "contains date header"
    (should= ["Date"] (keys (:headers (create-response 200)))))

  (for [status [200 300]
        content ["Hello, world!" "Adios, world!"]]
    (it (format "contains status %s with body content: %s" status content)
      (let [res (create-response status content)]
        (should= status (:status res))
        (should= (map identity (.getBytes content))
                 (map identity (:body (create-response status content)))))))

  (for [status [200 400]
        content ["Hello, world!" "Adios, world!"]
        headers [{"Content-Type" "text/html" "Content-Length" "3" "Date" "123"}
                 {"Content-Length" "14" "Content-Type" "text/plain" "Date" "321"}]]
    (it (format "contains status %s, content %s, and overrides headers" status content headers)
      (let [res (create-response status content headers)]
        (should= status (:status res))
        (should= headers (:headers res))
        (should= (map identity (.getBytes content)) (map identity (:body res)))))))
