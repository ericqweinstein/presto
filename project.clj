(defproject presto "0.1.0"
  :description "Presto: a web application to monitor our current coffee condition"
  :url "http://coffee.ericweinste.in/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [liberator "0.13"]
                 [compojure "1.4.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/clojurescript "1.7.145"]
                 [com.taoensso/carmine "2.12.0"]
                 [clj-http "2.0.0"]
                 [org.clojure/data.json "0.2.6"]]
  :profiles {:dev {:plugins [[lein-cljsbuild "1.0.3"]
                             [lein-ancient "0.5.5"]
                             [lein-kibit "0.0.8"]
                             [lein-bikeshed "0.1.8"]
                             [codox "0.8.10"]]}}
  :cljsbuild {
    :builds [{
      ;; The path to the top-level ClojureScript source directory.
      :source-paths ["src/cljs"]
      ;; Pretty standard development compiler options.
      ;; We'll figure out minification/uglification later.
      :compiler {
        :output-to "resources/public/js/main.js"
        :optimizations :advanced
        :externs ["resources/public/js/externs.js"]}}]}
  :aliases {
            "rebuild" ^{:doc "Clean ClojureScript assets, then build them once."}
            ["do" "cljsbuild" "clean," "cljsbuild" "once"]
            "lint" ^{:doc "Lint our Clojures."}
            ["do" "ancient," "kibit," ["bikeshed", "-m100", "-v"]]}
  :main presto.server)
