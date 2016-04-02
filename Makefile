
all:
	mvn package
	rm -rf build/*
	cp target/*.jar build
