(ns enne.pic
  (:require [clojure.pprint :as pprint]
            [clojure.test.check.generators :as gen])
  #?(:clj (:import (java.util Calendar)))
  #?(:cljs (:require-macros [clojure.test.check.generators :as gen])))


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


(def ^:private control-characters
  {0  0
   1  1
   2  2
   3  3
   4  4
   5  5
   6  6
   7  7
   8  8
   9  9
   10 \A
   11 \B
   12 \C
   13 \D
   14 \E
   15 \F
   16 \H
   17 \J
   18 \K
   19 \L
   20 \M
   21 \N
   22 \P
   23 \R
   24 \S
   25 \T
   26 \U
   27 \V
   28 \W
   29 \X
   30 \Y})


(defn- control-character
  [date-of-birth individual-number]
  (-> (str date-of-birth individual-number)
      (parse-int)
      (rem 31)
      (control-characters)))


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
   (gen/let [year (gen/choose year-lower-bound year-upper-bound)
             month (gen/choose 1 12)
             day (gen/choose 1 (last-day-of-month year month))
             individual-number (gen/fmap (partial zero-pad 3) (individual-number-generator sex))]
     (let [date-of-birth (str (zero-pad 2 day)
                              (zero-pad 2 month)
                              (subs (str year) 2))]
       (str date-of-birth
            (century-code year)
            individual-number
            (control-character date-of-birth individual-number)))))
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
