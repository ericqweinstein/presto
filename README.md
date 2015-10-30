Presto
======
Presto is a [Flit](https://github.com/ericqweinstein/flit) client: it gets, interprets, and displays information about your C3 (Current Coffee Condition).

Presto is written in Clojure (see below). It implements HTCPCP to the extent that makes any sense; see [RFC 2324](https://tools.ietf.org/html/rfc2324) for more.

## Usage
<p align="center">
  <img src="https://s3.amazonaws.com/f.cl.ly/items/1g3A2R0Z2b1A25354741/presto-coffee.png" />
</p>

## Stack
#### Front end
* [ClojureScript](https://github.com/clojure/clojurescript) 1.7 (language, compiles to JavaScript)
* [Bootstrap](http://getbootstrap.com/) (CSS only)
* [D3.js](http://d3js.org/) (graphics/animation library)

#### Back end
* [Clojure](http://clojure.org/) 1.7 (language)
* [Compojure](https://github.com/weavejester/compojure) (routing)
* [Hiccup](https://github.com/weavejester/hiccup) (view rendering)
* [Liberator](http://clojure-liberator.github.io/liberator/) (API)
* [Carmine](https://github.com/ptaoussanis/carmine) (Redis client)
* [Redis](http://redis.io/) (data store)

## Dependencies
* Java 6+ (**note**: the web server will work fine on Java 8, but [Google Closure Compiler](https://github.com/clojure/clojurescript) won't)
* [Leiningen 2](http://leiningen.org/)

## Running locally
1. Clone (`λ git clone https://github.com/RentTheRunway2/presto.git`)
2. Install dependencies (`λ lein deps :tree`)
3. Clean and build assets (`λ lein rebuild`)
4. Start the web server (`λ lein run -m presto.server 8080`) (or whichever port you prefer)

## Contributing
1. Branch (`λ git checkout -b fancy-new-feature`)
2. Commit (`λ git commit -m "Fanciness!"`)
3. Test (`λ lein test`)
4. Push (`λ git push origin fancy-new-feature`)
5. Ye Olde Pulle Requeste

## License
Copyright © 2015 Eric Weinstein
Distributed under the Eclipse Public License 1.0, the same as Clojure.
