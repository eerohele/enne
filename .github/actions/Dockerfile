FROM clojure:tools-deps-alpine
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN clojure -Sforce -A:test
CMD ["clojure", "-A:test:test/clj"]
