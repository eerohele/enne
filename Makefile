.PHONY: test deploy

test:
	@clj -Atest

deploy: test
	@clj -Spom
	@mvn deploy
