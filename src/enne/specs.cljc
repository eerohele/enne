(ns enne.specs
  (:require [clojure.spec.alpha :as spec]
            [enne.gen :as gen]))


(spec/def ::last-name
  (spec/with-gen string? (constantly gen/last-name)))


(spec/def ::female-first-name
  (spec/with-gen string? (constantly gen/female-first-name)))


(spec/def ::female-middle-name
  (spec/with-gen string? (constantly gen/female-middle-name)))


(spec/def ::female-first-names
  (spec/with-gen string? (constantly gen/female-first-names)))


(spec/def ::female-name
  (spec/with-gen string? (constantly gen/female-name)))


(spec/def ::male-first-name
  (spec/with-gen string? (constantly gen/male-first-name)))


(spec/def ::male-middle-name
  (spec/with-gen string? (constantly gen/male-middle-name)))


(spec/def ::male-first-names
  (spec/with-gen string? (constantly gen/male-first-names)))


(spec/def ::male-name
  (spec/with-gen string? (constantly gen/male-name)))


(spec/def ::first-names
  (spec/with-gen string? (constantly gen/first-names)))


(spec/def ::rand-name
  (spec/with-gen string? (constantly gen/rand-name)))


(spec/def ::municipality
  (spec/with-gen string? (constantly gen/municipality)))


(spec/def ::municipality-name
  (spec/with-gen string? (constantly gen/municipality-name)))


(spec/def ::personal-identity-code
  (spec/with-gen (spec/and string? #(= (count %) 11))
                 (constantly gen/personal-identity-code)))


(spec/def ::female-personal-identity-code
  (spec/with-gen ::personal-identity-code
                 (constantly gen/female-personal-identity-code)))


(spec/def ::male-personal-identity-code
  (spec/with-gen ::personal-identity-code
                 (constantly gen/male-personal-identity-code)))


(comment
  (spec/exercise ::last-name)

  (spec/exercise ::female-first-name)
  (spec/exercise ::female-middle-name)
  (spec/exercise ::female-first-names)
  (spec/exercise ::female-name)

  (spec/exercise ::male-first-name)
  (spec/exercise ::male-middle-name)
  (spec/exercise ::male-first-names)
  (spec/exercise ::male-name)

  (spec/exercise ::first-names)
  (spec/exercise ::rand-name)
  (spec/exercise ::municipality)
  (spec/exercise ::municipality-name)

  (spec/exercise ::personal-identity-code)
  (spec/exercise ::female-personal-identity-code)
  (spec/exercise ::male-personal-identity-code)
  )
