(ns enne.gen
  (:require [clojure.pprint :as pprint]
            [clojure.string :as string]
            [clojure.test.check.generators :as gen]
            [enne.core :refer [as-string]]
            [enne.data :as data])
  #?(:cljs (:require-macros [clojure.test.check.generators :as gen])))


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

(def ^:private municipality-codes
  (mapv first data/municipalities))


(def municipality
  (gen/elements municipality-codes))


(def ^:private municipality-names
  (mapv second data/municipalities))


(def municipality-name
  (gen/elements municipality-names))


(defn- century-code
  [year]
  (cond (<= 1800 year 1899) \+
        (<= 1900 year 1999) \-
        :else \A))


(defn- day-of-month
  [month]
  (cond (= month 2) (gen/choose 28 29)
        (some #{1 3 5 7 8 10 12} #{month}) (gen/return 31)
        :else (gen/return 30)))


(defn- parse-int
  [s]
  #?(:clj  (Integer/parseInt s)
     :cljs (let [int (js/parseInt s)]
             (if (js/isNaN int) nil int))))


(def ^:private control-code-character-map
  (into (zipmap (range) (range 10))
        (zipmap (range 10 31)
                (disj (into (sorted-set) (map char (range 65 90)))
                      \G \I \O \Q \Z))))


(defn- control-character
  [date-of-birth individual-number]
  (get control-code-character-map
       (rem (parse-int (str date-of-birth individual-number)) 31)))


(defn- zero-pad
  [to number]
  (pprint/cl-format nil (str "~" to ",'0d") number))


(def personal-identity-code
  (gen/let [year              (gen/choose 1800 2018)
            month             (gen/choose 1 12)
            day               (day-of-month month)
            individual-number (gen/choose 2 899)]
    (let [date-of-birth (str (zero-pad 1 day)
                             (zero-pad 2 month)
                             (zero-pad 1 (subs (str year) 2)))]
      (str date-of-birth
           (century-code year)
           (zero-pad 3 individual-number)
           (control-character date-of-birth individual-number)))))


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

  (gen/sample municipality)
  (gen/sample municipality-name)
  (gen/sample personal-identity-code)
  )
