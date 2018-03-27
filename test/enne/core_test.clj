(ns enne.core-test
  (:require [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [enne.core :as enne]
            [enne.data :as data]))


(def name-seq? (every-pred seq? #(<= 2 (count %) 4) (partial every? string?)))


(def last-names (-> data/names :last set))
(def female-first-names (-> data/names :female/first set))
(def female-middle-names (-> data/names :female/middle set))
(def male-first-names (-> data/names :male/first set))
(def male-middle-names (-> data/names :male/middle set))


(defspec generates-n-names
  100
  (prop/for-all
    [n gen/pos-int]
    (let [names (enne/female-names n)]
      (and (= (count names) n) (every? name-seq? names)))))


(defspec generate-individual-name-parts
  100
  (prop/for-all
    [_ gen/pos-int]
    (contains? last-names (enne/last-name))
    (contains? female-first-names (enne/female-first-name))
    (contains? female-middle-names (enne/female-middle-name))
    (contains? male-first-names (enne/male-first-name))
    (contains? male-middle-names (enne/male-middle-name))))
