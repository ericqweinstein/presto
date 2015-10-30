(ns ^{:doc "All our fancy custom ClojureScripts."
      :author "Eric Weinstein <eric.q.weinstein@gmail.com>"}
  presto.client
  (:require [goog.net.XhrIo :as xhr]))

(defn dev-message
  "Adds a console message for any developers
  checking out our sweet sweet coffee magicks."
  []
  (.log js/console
    "Thanks for visiting! More at: github.com/ericqweinstein"))

(defn create-svg-canvas
  "Creates an SVG 'canvas' to which we'll append all our D3 animations."
  [element height width]
  (.attr
    (.attr
      (.append
        (.select js/d3 element) "svg") "height" height) "width" width))

(defn write-message
  "Writes a message corresponding to the amount of coffee."
  [level]
  (let [container (.getElementById js/document "container")
        button (.getElementById js/document "trends")
        text (cond
               (<= level 100) "The pot's off the sensor."
               (<= level 700) "Coffee's low—better make some more!"
               (<= level 775) "There's about half a pot."
               (<= level 800) "There's plenty of coffee."
               :else "Pot's full!")
        paragraph (.createElement js/document "p")
        textNode (.createTextNode js/document text)]
    (.appendChild paragraph textNode)
    (.setAttribute paragraph "id" "message")
    (.insertBefore container paragraph button)))

(defn add-bubbles
  "Adds bubbles to the coffee. (Animation is handled by CSS.)"
  [cup]
  (let [coffee (.select js/d3 cup)
        dataSet (array 0 0 0 0 0 0 0)]
    (-> coffee
      (.selectAll ".bubble")
      (.data dataSet)
      (.enter)
      (.append "div")
      (.attr "class" "bubble"))))

(defn draw-coffee
  "Draws a rectangle reflecting the coffee level on the UI."
  [level initial-height width]
  (let [canvas (create-svg-canvas ".coffee" 154 150)
        endHeight (.round js/Math (/ level 6))
        x (.linear (.-scale js/d3))
        y (.linear (.-scale js/d3))]
    (-> x
      (.domain (array 0 1))
      (.range (array 0 150)))
    (-> y
      (.domain (array 0 100))
      (.rangeRound (array 0 initial-height)))
    (-> canvas
      (.selectAll "rect")
      (.data (array endHeight))
      (.enter)
      (.append "rect")
      (.attr "x" (fn [] initial-height))
      (.attr "y" 154)
      (.attr "width" width)
      (.attr "fill" "#532700")
      (.attr "rx" 8)
      (.attr "ry" 8)
      (.transition)
      (.duration 500)
      (.attr "height" (fn [] endHeight))
      (.attr "y" (fn [] (- 154 endHeight)))))

  (add-bubbles ".coffee")
  (write-message level))

(defn coffee-state
  "Parses the AJAX response from the server, which is
  a JSON array containing a single object. On success,
  draws the coffee level on the UI."
  [event]
  (let [response (.-target event)]
    (draw-coffee (aget (.getResponseJson response) "0" "state") 4 152)))

(defn get-coffee-state
  "Makes an AJAX request to the server."
  [url]
  (xhr/send url coffee-state "GET" nil #js {"X-Accept-Additions" "Whisky"}))

(defn ^:export main
  "The entry point for all our ClojureScript magicks."
  []
  (get-coffee-state "/api/coffee")
  (dev-message))
