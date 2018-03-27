(ns enne.core
  "Generate random Finnish names from data supplied by the Finnish Population Register Centre.

  The data is extracted from Excel spreadsheets available at avoindata.fi. If new Excel spreadsheets are made available,
  update the URLs in resource/source.edn and run (enne.data/retrieve!)"
  (:require [clojure.string :as string]
            [enne.data :as data]))


(def ^:private middle-name-odds
  "The odds for having 0-2 middle names."
  {0.05 0
   0.55 2
   1.00 1})


(defn- number-of-middle-names
  "Conjure a random number (between 0 and 2) of middle names to generate."
  []
  (let [n (rand)]
    (second (first (drop-while #(< (key %) n) middle-name-odds)))))


(defn last-name
  []
  (rand-nth (:last data/names)))


(defn female-first-name
  []
  (rand-nth (:female/first data/names)))


(defn female-middle-name
  []
  (rand-nth (:female/middle data/names)))


(defn male-first-name
  []
  (rand-nth (:male/first data/names)))


(defn male-middle-name
  []
  (rand-nth (:male/middle data/names)))


(defn generate
  "Generate an infinite sequence of names."
  [last first middle]
  (repeatedly #(list* (rand-nth last) (rand-nth first)
                      (take (number-of-middle-names) (repeatedly (partial rand-nth middle))))))


(defn female-names
  "Generate an infinite sequence of (or `n` if given) female names.

  Returns a list where the first item is the last name and the rest are first names.

  Example:

    (female-names 1)
    => ((Brännare Anais Cassandra Therese))"
  ([]
   (generate (:last data/names) (:female/first data/names) (:female/middle data/names)))
  ([n]
   (take n (female-names))))


(defn male-names
  "Generate an infinite sequence of (or `n` if given) male names.

  Returns a list where the first item is the last name and the rest are first names.

  Example:

    (male-names 1)
    => ((Höylä Jared Roobert))"
  ([]
   (generate (:last data/names) (:male/first data/names) (:male/middle data/names)))
  ([n]
   (take n (male-names))))


(defn male-name
  []
  (-> 1 male-names first))


(defn female-name
  []
  (-> 1 female-names first))


(defn rand-name
  []
  ((rand-nth [male-name female-name])))


(defn as-string
  "Turn a name into a string."
  [[last-name & first-names]]
  (str last-name ", " (string/join " " first-names)))


(defn as-strings
  "Turn a list of names into strings.

  Example:

    (strings (male-names 3))
    => (\"Aartola, Süleyman Altti Joonathan\" \"Sarajärvi, Mio Samu Erno\" \"Yömaa, Romeo Phuc Aabel\")"
  [names]
  (map as-string names))
