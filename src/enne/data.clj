(ns enne.data
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [dk.ative.docjure.spreadsheet :as spreadsheet])

  (:refer-clojure :exclude [load])

  (:import (java.io PushbackReader)))


(defn- load-edn
  [source]
  (with-open [r (io/reader source)]
    (edn/read (PushbackReader. r))))


(def ^:private data
  {:first-names (-> "resources/first-names.edn" load-edn delay)
   :last-names  (-> "resources/last-names.edn" load-edn delay)})


(def names
  (let [{:keys [last-names first-names]} data]
    {:last          (get @last-names "Nimet")

     :female/first  (get @first-names "Naiset ens")
     :female/middle (get @first-names "Naiset muut")

     :male/first    (get @first-names "Miehet ens")
     :male/middle   (get @first-names "Miehet muut")}))


(defn- spreadsheet->edn
  "Turn an Excel workbook into a hash map of sheet name to first cell in every row."
  [workbook]
  (reduce (fn [acc sheet]
            (let [rows  (->> (spreadsheet/row-seq sheet) (remove nil?) rest)
                  cells (map spreadsheet/cell-seq rows)]
              (assoc acc (spreadsheet/sheet-name sheet)
                         (map first (map (partial map spreadsheet/read-cell) cells)))))
          {}
          (spreadsheet/sheet-seq workbook)))


(defn- load
  [url]
  (-> url
      io/input-stream
      spreadsheet/load-workbook-from-stream
      spreadsheet->edn
      (dissoc "Saate")
      pr-str))


(defn retrieve!
  "Retrieve name data from the Finnish open data service and save it into EDN files."
  ([output-file input-url]
   (spit output-file (load input-url)))
  ([]
   (let [{:keys [last-names first-names]} (load-edn "resources/source.edn")]
     (retrieve! "resources/last-names.edn" last-names)
     (retrieve! "resources/first-names.edn" first-names))))

(comment
  (retrieve!)
  )
