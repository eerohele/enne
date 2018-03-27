# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

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
