(ns ^{:doc "Our various views. (Since there are only
           a handful, I've put them all in one file.)"
      :author "Eric Weinstein <eric.q.weinstein@gmail.com>"}
  presto.views.contents
  (:use [hiccup.element :only (link-to)]))

(defn index
  "Markup for the index view."
  []
  [:div {:class "col-md-6 col-md-offset-3" :id "container"}
    [:div {:class "cup"}
     [:div {:class "coffee"}]]
   (link-to {:class "btn btn-primary" :id "trends"} "/trends" "View Trends")])

(defn trends
  "Markup for the trends view."
  []
  [:div {:class "col-md-12 page-title"}
   [:h1 "Trends"]
   [:p "None yet; check back soon."]
   [:br]
   (link-to {:class "btn btn-primary"} "/" "Home")])

(defn not-found
  "Markup for the 404 page."
  []
  [:div {:class "col-md-12 page-title"}
   [:h1 {:class "text-muted"} "418: I'm a teapot"]
   [:p {:class "text-muted"}
    "(Short and stout.) The page you're looking for doesn't exist!"]
   [:br]
   (link-to {:class "btn btn-primary"} "/" "Home")])
