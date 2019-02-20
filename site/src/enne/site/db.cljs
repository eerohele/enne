(ns enne.site.db
  (:require [clojure.test.check.generators :as gen]
            [reagent.core :as reagent]
            [enne.core :as enne]
            [enne.pic :as pic]))


(defonce db
  (reagent/atom {:year   {:lower-bound 1800
                          :upper-bound (.getFullYear (js/Date.))}

                 :sex    :sex/female

                 :person {:name         (enne/rand-name :sex/female)
                          :pic          (gen/generate (pic/generator :sex/female 1800 (.getFullYear (js/Date.))))
                          :municipality (enne/municipality)}}))


(defn person-name
  []
  (enne/as-string (get-in @db [:person :name])))


(defn municipality
  []
  (last (get-in @db [:person :municipality])))


(defn personal-identity-code
  []
  (get-in @db [:person :pic]))

(defn year
  [bound]
  (get-in @db [:year bound]))


(defn set-year
  [bound event]
  (swap! db assoc-in [:year bound] (js/parseInt (.. event -target -value))))


(defn set-sex
  [sex]
  (swap! db assoc :sex sex))

(defn pic
  [sex year-lower-bound year-upper-bound]
  (gen/generate (pic/generator sex year-lower-bound year-upper-bound)))


(defn random-finn
  []
  (let [{:keys [:sex :year]} @db]
    {:name         (enne/rand-name sex)
     :pic          (gen/generate (pic/generator sex (:lower-bound year) (:upper-bound year)))
     :municipality (enne/municipality)}))


(defn make-person
  []
  (swap! db assoc :person (random-finn)))
