install:
	mkdir -p jqwik
	mvn clean install
	mvn source:jar install