(ns enne.edn
  #?(:clj
     (:require [clojure.edn :as edn]
               [clojure.java.io :as io]))
  #?(:clj
     (:import (java.io PushbackReader))))

#?(:clj (do
          (defmacro load-resource-m
            [source]
            (with-open [r (-> source io/resource io/reader)]
              (edn/read (PushbackReader. r))))

          (defn load-resource-fn
            [source]
            (with-open [r (-> source io/resource io/reader)]
              (edn/read (PushbackReader. r))))))

