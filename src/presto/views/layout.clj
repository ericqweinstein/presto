(ns ^{:doc "The layout for all our views."
      :author "Eric Weinstein <eric.q.weinstein@gmail.com>"}
  presto.views.layout
  (:use [hiccup.page :only (html5 include-css include-js)]))

(defn application
  "Renders the layout for all pages."
  [title & content]
  (html5 {:lang "en"}
         [:head
          [:title title]
          (include-css "css/vendor/bootstrap.css")
          (include-css "css/vendor/bootstrap-theme.css")
          (include-css "css/main.css")
          [:link {:rel "apple-touch-icon" :href "favicon.ico"}]]
         [:body
          [:div {:class "jumbotron"}
           [:h1 "Presto!"
            [:small "Monitoring our C3 (Current Coffee Condition)"]]]
          [:div {:class "row" :id "page-content"} content]
          (include-js "js/vendor/goog/base.js")
          (include-js "js/vendor/d3.js")
          (include-js "js/main.js")
          [:script {:type "text/javascript"} "goog.require('presto.client');"]
          [:script {:type "text/javascript"} "presto.client.main();"]]))
