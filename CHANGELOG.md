# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

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
