(ns ^{:doc "Presto's web server."
      :author "Eric Weinstein <eric.q.weinstein@gmail.com>"}
  presto.server
  (:require [liberator.core :refer [resource defresource]]
            [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [defroutes GET ANY]]
            [compojure.route :as route]
            [presto.views.layout :as layout]
            [presto.views.contents :as contents]
            [presto.redis :as redis]))

(def recruiting-header
  "Shameless self-promotion."
  "If you're reading this, you should probably be working at CityShelf:
  http://www.cityshelf.com/")

(defroutes routes
  "Application routes."
  ;; Loads the main view (depicting how much coffee there is).
  (GET "/" [] {:headers {"X-Recruiting" recruiting-header
                         "Content-Type" "text/html; charset=utf-8"}
               :body (layout/application "Presto!" (contents/index))})

  ;; Serves static assets.
  (route/files "/" {:root "resources/public"})

  ;; Loads the history view (showing coffee consumption trends).
  (GET "/trends" [] (layout/application "Trends" (contents/trends)))

  ;; Gets the coffee state (an int representing how full the pot is).
  (GET "/api/coffee" [] (resource :available-media-types ["application/json"]
                                  :handle-ok (fn [context]
                                               (redis/coffee-data))))

  ;; Catch-all 418 (well, technically a 404) route.
  (ANY "*" []
       (route/not-found (layout/application "418!" (contents/not-found)))))

(def handler
  "Handle all routes."
  (-> routes))

(defn -main
  "Starts the webserver."
  [port]
  (run-jetty #'handler {:port (Integer. port) :join? false}))
