(ns enne.core-test
  (:require [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [enne.core :as enne]))


(def name? (every-pred seq? #(<= 2 (count %) 4) (partial every? string?)))


(defspec generates-n-names
  100
  (prop/for-all
    [n gen/pos-int]
    (let [names (enne/female-names n)]
      (and (= (count names) n) (every? name? names)))))
