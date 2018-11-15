Enne
====

[![Clojars Project](https://img.shields.io/clojars/v/me.flowthing/enne.svg)](https://clojars.org/me.flowthing/enne)
[![CircleCI](https://circleci.com/gh/eerohele/enne.svg?style=svg)](https://circleci.com/gh/eerohele/enne)

Do you need to come up with totally realistic names for the scores of Finnish
characters in your upcoming speculative fiction novel set in dystopic
Fennoscandia, replete with Silmarillion-style mythology?

Do you also like parentheses and etymology?

You're in luck.

## Try

Install [Clojure CLI tools](https://clojure.org/guides/getting_started#_clojure_installer_and_cli_tools).

Then, on the command line:

```bash
$ clj -Sdeps '{:deps {me.flowthing/enne {:mvn/version "0.4.0"}}}'
```

Then, in the REPL:

```clojure
(use 'enne.core)

;; Ladies
(female-names 5)
;;=>
;;(("Mäihäniemi" "Marlen" "Trang" "Mariela")
;; ("Estola" "Zandra" "Alinda" "Siru")
;; ("Östlund" "Anastasija" "Merina" "Danielsdotter")
;; ("Gävert" "Leena-Marja" "Abdullahi")
;; ("Aittakallio" "Sannaleena"))

;; Gentlemen
(male-names 5)
;;=>
;;(("Telkki" "Reza" "Edel" "Armin")
;; ("Knutsson" "Diar" "Wiljam")
;; ("Rutonen" "Kaapro" "Ville")
;; ("Suur-Hamari" "Arnold" "Anthon" "Lennox")
;; ("Mårtens" "Elliot" "Harri" "Kaapro"))

;; Mix it up, bro
(shuffle (interleave (female-names 5) (male-names 5)))
;;=>
;;[("Teppinen" "Amelie" "Riitta-Leena")
;; ("Kiviranta" "Solmu" "Alve" "Leonidas")
;; ("Kiikkala" "Shannon" "Theresa" "Katie")
;; ("Alaruikka" "Daan" "Karlo")
;; ("Röyskö" "Henri-Pekka" "Otava" "Ilari")
;; ("Paijola" "Farhia" "Mohamed" "Mukhtar")
;; ("Haipola" "Sahro" "Tessaliina" "Francesca")
;; ("Pelttari" "Andrus" "Urbanus")
;; ("Volanen" "Tiia-Maaria" "Nicolette")
;; ("Iso-Heiniemi" "Okko" "Börje" "Karsten")]

;; Strings, too
(-> 5 female-names as-strings)
;;=>
;;("Ripatti, Ekaterina Clarisse Milana"
;; "Homanen, Eeva-Riitta Therése Adele"
;; "Kantele, Santra Tyyne Marjukka"
;; "Brännback, Anu-Maaria Kerttuli"
;; "Kiira, Ave Inari Natacha")

;; Single name
(as-string (male-name))
;;=> "Melin, Kivi Ewert"
(as-string (female-name))
;;=> "Berndtson, Nessi Marjaana"

;; Don't care about sex
(as-string (rand-name))
;;=> "Viitalähde, Sten Magnusson"

;; Last names, first names, middle names
(last-name)
;;=> "Harmokivi"
(female-first-name)
;;=> "Venera"
(male-middle-name)
;;=> "Pasinpoika"

;; A Finnish municipality (code and name)
(municipality)
;;=> ["560" "Orimattila"]
```

## Generators and specs

```clojure
(require '[clojure.test.check.generators :refer [generate]]
         '[enne.gen :as gen])

(generate gen/rand-name)
;;=> "Alalääkkölä, Runar Carl-Henrik"

(require '[clojure.spec.alpha :as spec]
         '[enne.specs :as names])

(spec/def ::person
    (spec/keys :req [::names/last-name ::names/first-names]))

(first (spec/exercise ::person 1))
;;=>{:last-name "Kolli", :first-names "Osama Joosef Rickhard"}

(generate gen/personal-identity-code)
;;=> "300994-644M"
```

## License

Copyright © 2018 Eero Helenius

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
