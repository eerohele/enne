(ns enne.pic
  (:require [clojure.pprint :as pprint]
            [clojure.test.check.generators :as gen])
  #?(:clj (:import (java.util Calendar))))


(defn- century-code
  [year]
  (cond (<= 1800 year 1899) \+
        (<= 1900 year 1999) \-
        :else \A))


(defn- leap-year?
  [year]
  (cond (not (zero? (mod year 4))) false
        (not (zero? (mod year 100))) true
        (zero? (mod year 400)) true
        :else false))


(defn- last-day-of-month
  [year month]
  (cond (= month 2) (if (leap-year? year) 29 28)
        (some #{1 3 5 7 8 10 12} #{month}) 31
        :else 30))


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
  [s]
  (get control-code-character-map (rem (parse-int s) 31)))


(defn- zero-pad
  [to number]
  (pprint/cl-format nil (str "~" to ",'0d") number))


(defn- individual-number-generator
  [sex]
  (cond->> (gen/choose 2 899)
           (= :sex/male sex) (gen/such-that odd?)
           (= :sex/female sex) (gen/such-that even?)))


(defn- current-year
  []
  #?(:clj  (.get (Calendar/getInstance) Calendar/YEAR)
     :cljs (.getFullYear (js/Date.))))


(defn generator
  "Create a generator that generates Finnish personal identity codes.

  By default, generates codes for both sexes. To generate codes for only one
  sex, pass in :sex/female or :sex/male as the first argument:

    (gen/generate (generator :sex/female))
    ;;=> \"180412A894S\"

  By default, generates codes where the birth date is between 1800 (the first
  century for which personal identity codes are applicable) and the current
  year. To limit codes to a specific year range, pass in the lower bound and,
  optionally, the upper bound:

    (gen/generate (generator :sex/any 1995 2015))
  "
  ([sex year-lower-bound year-upper-bound]
   (gen/let [year              (gen/choose year-lower-bound year-upper-bound)
             month             (gen/choose 1 12)
             day               (gen/choose 1 (last-day-of-month year month))
             individual-number (gen/fmap (partial zero-pad 3) (individual-number-generator sex))]
     (let [date-of-birth (str (zero-pad 2 day)
                              (zero-pad 2 month)
                              (subs (str year) 2))]
       (str date-of-birth
            (century-code year)
            individual-number
            (control-character (str date-of-birth individual-number))))))
  ([sex year-lower-bound]
   (generator sex year-lower-bound (current-year)))
  ([sex]
   (generator sex 1800 (current-year)))
  ([]
   (generator :sex/any 1800 (current-year))))


(comment
  (gen/generate (generator :sex/any))
  (gen/generate (generator :sex/female))
  (gen/generate (generator :sex/male))
  (gen/generate (generator :sex/any 1995 2015))
  )
