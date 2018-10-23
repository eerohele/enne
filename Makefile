.PHONY: test deploy

test:
	@clj -Atest

pom: pom.xml
	@clj -Spom

deploy: test
	@mvn deploy
