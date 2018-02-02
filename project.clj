(defproject me.flowthing/enne "0.1.0"
  :description "Generate random Finnish names."

  :url "https://github.com/eerohele/enne"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [dk.ative/docjure "1.12.0"]]

  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[com.taoensso/tufte "1.1.2"]
                                  [org.clojure/test.check "0.10.0-alpha2"]]}})
