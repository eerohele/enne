(ns enne.gen
  (:require [clojure.string :as string]
            [clojure.test.check.generators :as gen]
            [enne.core :refer [as-string]]
            [enne.data :as data]))


(def last-name
  (gen/elements (:last data/names)))


(def female-first-name
  (gen/elements (:female/first data/names)))


(def female-middle-name
  (gen/elements (:female/middle data/names)))


(defn ^:private first-name-gen
  [fn mn]
  (gen/one-of
    [(gen/let [m1 mn
               m2 mn
               f  fn]
       (string/join " " [f m1 m2]))

     (gen/let [m mn
               f fn]
       (string/join " " [f m]))

     fn]))


(def female-first-names
  (first-name-gen female-first-name female-middle-name))


(def female-name
  (gen/let [l  last-name
            fn female-first-names]
    (as-string [l fn])))


(def male-first-name
  (gen/elements (:male/first data/names)))


(def male-middle-name
  (gen/elements (:male/middle data/names)))


(def male-first-names
  (first-name-gen male-first-name male-middle-name))


(def male-name
  (gen/let [l  last-name
            fn male-first-names]
    (as-string [l fn])))


(def first-names
  (gen/one-of [female-first-names male-first-names]))


(def rand-name
  (gen/one-of [female-name male-name]))


(def ^:private municipality-names
  (mapv second data/municipalities))


(def municipality-name
  (gen/elements municipality-names))


(comment
  (gen/sample last-name)

  (gen/sample female-first-name)
  (gen/sample female-middle-name)
  (gen/sample female-first-names)
  (gen/sample female-name)

  (gen/sample male-first-name)
  (gen/sample male-middle-name)
  (gen/sample male-first-names)
  (gen/sample male-name)

  (gen/sample first-names)
  (gen/sample rand-name)

  (gen/sample municipality-name))
