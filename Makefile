.PHONY: test pom deploy

test:
	@clj -Atest

pom: pom.xml
	@clj -Spom

deploy: test, pom
	@mvn deploy
