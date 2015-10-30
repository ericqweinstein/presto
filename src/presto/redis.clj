(ns ^{:doc "Redis magicks."
      :author "Eric Weinstein <eric.q.weinstein@gmail.com>"}
  presto.redis
  (:require [taoensso.carmine :as car :refer (wcar)]
            [clj-http.client :as client]
            [clojure.data.json :as json]))

(def flit-ip
  "Get the Flit IP address from the environment."
  (str "http://" (get (System/getenv) "COFFEE_SERVER")))

(def redis-uri
  "Get the Redis conn string from the environment."
  (get (System/getenv) "REDISTOGO_URL"))

(def server1-conn
  "Set up a single Redis server pool."
  {:pool {} :spec {:uri redis-uri}})

(defmacro wcar*
  "Handle the connection pool."
  [& body]
  `(car/wcar server1-conn ~@body))

(defn initialize-coffee
  "Sets the coffee data Redis key if needed."
  []
  (if-not
    (wcar* (car/get "coffee"))
    (wcar* (car/set "coffee"
                    {:time (System/currentTimeMillis) :level 0}))))

(defn redis-coffee
  "Get coffee data from Redis."
  []
  (let [amount ((wcar* (car/get "coffee")) :level)]
    (format "[{\"state\": %d}]" amount)))

(defn http-coffee
  "Get coffee data from Flit and update the Redis cache."
  []
  (let [level ((client/get flit-ip {:accept :json}) :body)]
    ;; Update the cache...
    (wcar*
      (car/set "coffee"
               {:time (System/currentTimeMillis)
                :level ((first
                          (json/read-str level :key-fn keyword))
                          :state)}))

    ;; ...and return the new coffee level.
    level))

(defn coffee-data
  "Get coffee data from the Redis cache if data < 5 minutes old;
  otherwise, get new data via HTTP request."
  []
  (initialize-coffee)
  (if
    (> ((wcar* (car/get "coffee")) :time) (- (System/currentTimeMillis) 300000))
    (redis-coffee)
    (http-coffee)))
