# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## 1.1.0 - 2021-09-30
- Add `male-first-names` and `female-first-names`

## 1.0.1 - 2020-06-02
- Add leading zeroes to municipality codes

  Officially, municipality codes always have three characters.

## 1.0.0 - 2020-02-24
- Use latest available name and municipality data
- [1.0 all the things](https://insideclojure.org/2020/02/18/lib-version/)

## 0.6.4 - 2019-02-06
- Remove unnecessary ClojureScript dependency

## 0.6.3 - 2018-11-21
- Remove unnecessary dependencies

## 0.6.2 - 2018-11-16
- Fix bug in PIC control character calculation

## 0.6.1 - 2018-11-15
- Fix day of month generation for PICs

## 0.6.0 - 2018-11-15
- Add support for generating Finnish Personal Identity Codes (HETU)

## 0.5.0 - 2018-11-14
- Add generator and spec for municipality codes

## 0.4.2 - 2018-10-23
- Fix CLJC compatibility issue in enne.gen

## 0.4.1 - 2018-09-28
- Fix ClojureScript compatibility issue in enne.gen

## 0.4.0 - 2018-04-28
- Add ClojureScript support
- Add test.check generators
- Add specs

## 0.3.0 - 2018-03-28
- Add support for municipalities via `(municipality)`

## 0.2.0 - 2018-03-27
- Update name data from avoindata.fi
- Expand API to make it easier to generate individual names or name parts:
  - `(rand-name)`, `(female-name)` and `(male-name)` to generate a single name
  - `(last-name)`, `(female-first-name)`, `(male-first-name)`, `(female-middle-name)` and `(male-middle-name)` to generate individual name parts
  - `(as-string)` to create a string representation of a single name

## 0.1.1 - 2018-02-12
- Fix resource loading

## 0.1.0 - 2018-02-01
- Initial release
