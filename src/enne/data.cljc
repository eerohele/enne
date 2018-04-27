(ns enne.data
  (:require [enne.edn :include-macros true :as edn]))


(def ^:private data
  ;; This seems really stupid, but I couldn't get it to work any other way.
  ;;
  ;; If I use a macro in both clj and cljs, the Java class becomes too large
  ;; to compile.
  {:first-names    (#?(:clj edn/load-resource-fn :cljs edn/load-resource-m)
                     "first-names.edn")
   :last-names     (#?(:clj edn/load-resource-fn :cljs edn/load-resource-m)
                     "last-names.edn")
   :municipalities (#?(:clj edn/load-resource-fn :cljs edn/load-resource-m)
                     "municipalities.edn")})


(def names
  (let [{:keys [last-names first-names]} data]
    {:last          (get last-names "Nimet")

     :female/first  (get first-names "Naiset ens")
     :female/middle (get first-names "Naiset muut")

     :male/first    (get first-names "Miehet ens")
     :male/middle   (get first-names "Miehet muut")}))


(def municipalities
  (:municipalities data))
