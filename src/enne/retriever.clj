(ns enne.retriever
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [dk.ative.docjure.spreadsheet :as spreadsheet]
            [enne.edn :as edn])

  (:refer-clojure :exclude [load]))


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
  (let [{:keys [last-names first-names municipalities]} (edn/load-resource-fn "source.edn")]
    (spit "resources/last-names.edn" (load-names-from-spreadsheet last-names))
    (spit "resources/first-names.edn" (load-names-from-spreadsheet first-names))
    (spit "resources/municipalities.edn" (load-municipalities-from-csv municipalities))))


(comment
  (retrieve!)
  )
