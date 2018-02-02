# Enne

Do you need to come up with totally realistic names for the scores of Finnish
characters in your upcoming speculative fiction novel set in dystopic
Fennoscandia, replete with Silmarillion-style mythology?

Do you also like parentheses and etymology?

You're in luck.

```clojure
(use 'enne.core)

(shuffle (interleave (female-names 100) (male-names 100)))
;;=>
;;(("Alopaeus" "Jirka" "Bernt" "Muse")
;; ("Fall" "Kati-Maria" "Elsiina")
;; ("Vanhatalo" "Josefin" "Suvituuli" "Oosa")
;; ("Sulin" "Hayat" "Ulla-Riitta" "Sointu")
;; ("Aladin" "Anne-Elina" "Elli-Maria" "Liia")
;; ;; etc.
;; )
```

## Installation

Seriously? Just clone this repo, fire up a REPL, and generate some names.

If you really want to use it as a library, though, open up an issue and I'll push it into Clojars.

## Examples

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
```

## License

Copyright © 2018 Eero Helenius

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
