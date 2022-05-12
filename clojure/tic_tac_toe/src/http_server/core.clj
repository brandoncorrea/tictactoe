(ns http-server.core
  (:require [clojure.string :as s])
  (:import [httpServer Server HttpResponse]
           [java.util.function Function]))

(defn- ->route [route]
  (if (= route "/")
    :/
    (keyword (s/replace (subs route 1) #"/" ":"))))

(defn ->http-request [request]
  {:method   (keyword (s/lower-case (.method request)))
   :uri      (->route (.uri request))
   :protocol (.protocol request)
   :body     (map identity (.body request))
   :headers  (.headers request)})

(defn- update-keys [f m]
  (into {} (for [[k v] m] [(f k) v])))
(def ->http-response (partial update-keys keyword))
(def ->HttpResponse (partial update-keys #(subs (str %) 1)))

(defn create-response
  ([status] (->http-response (HttpResponse/create status)))
  ([status body] (->http-response (HttpResponse/create status body)))
  ([status body headers]
   (->http-response (HttpResponse/create status headers body))))

(defn route [req routes]
  (if-let [controller (or ((:uri req) routes) (:* routes))]
    (if-let [method ((:method req) controller)]
      (method req)
      (create-response 405))
    (create-response 404)))

(defn- create-router [routes]
  (reify Function
    (apply [_ req]
      (->HttpResponse (route (->http-request req) routes)))))

(defn create-server
  ([routes] (create-server 80 routes))
  ([port routes] (Server. port (create-router routes))))

(defn listen [server] (.listen server))
