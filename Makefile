.PHONY: test deploy

test:
	@clj -A:test:test/clj

deploy: test
	@clj -Spom
	@mvn deploy
