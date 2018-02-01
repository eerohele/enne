(ns enne.core-test
  (:require [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [enne.core :as enne]))


;; What a tremendous test, man.
(defspec generates-n-names
  100
  (prop/for-all [n gen/pos-int]
                (let [names (enne/female-names n)]
                  (and (= (count names) n) (every? seq? names)))))
