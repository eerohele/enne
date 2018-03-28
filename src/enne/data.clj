(ns enne.data
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.data.csv :as csv]
            [dk.ative.docjure.spreadsheet :as spreadsheet])

  (:refer-clojure :exclude [load])

  (:import (java.io PushbackReader)))


(defn- load-edn
  [source]
  (with-open [r (io/reader source)]
    (edn/read (PushbackReader. r))))


(def ^:private data
  {:first-names    (-> "first-names.edn" io/resource load-edn)
   :last-names     (-> "last-names.edn" io/resource load-edn)
   :municipalities (-> "municipalities.edn" io/resource load-edn)})


(def names
  (let [{:keys [last-names first-names]} data]
    {:last          (get last-names "Nimet")

     :female/first  (get first-names "Naiset ens")
     :female/middle (get first-names "Naiset muut")

     :male/first    (get first-names "Miehet ens")
     :male/middle   (get first-names "Miehet muut")}))


(def municipalities
  (:municipalities data))


(defn- spreadsheet->edn
  "Turn an Excel workbook into a hash map of sheet name to first cell in every row."
  [workbook]
  (reduce (fn [acc sheet]
            (let [rows  (->> (spreadsheet/row-seq sheet) (remove nil?) rest)
                  cells (map spreadsheet/cell-seq rows)]
              (assoc acc (spreadsheet/sheet-name sheet)
                         (mapv first (map (partial map spreadsheet/read-cell) cells)))))
          {}
          (spreadsheet/sheet-seq workbook)))


(defn- load-names-from-spreadsheet
  [url]
  (-> url
      io/input-stream
      spreadsheet/load-workbook-from-stream
      spreadsheet->edn
      (dissoc "Saate")
      pr-str))


(defn- load-municipalities-from-csv
  [url]
  (let [data (with-open [reader (io/reader (io/as-url url))]
               (doall
                 (csv/read-csv reader :separator \;)))]
    (-> (mapv (comp vec (partial take 2)) (rest data)) vec pr-str)))


(defn retrieve!
  "Retrieve name data from the Finnish open data service and save it into EDN files."
  []
  (let [{:keys [last-names first-names municipalities]} (load-edn (io/resource "source.edn"))]
    (spit "resources/last-names.edn" (load-names-from-spreadsheet last-names))
    (spit "resources/first-names.edn" (load-names-from-spreadsheet first-names))
    (spit "resources/municipalities.edn" (load-municipalities-from-csv municipalities))))

(comment
  (retrieve!)
  )
