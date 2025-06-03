#Makefile para coompilar y correr con maven
.PHONY: all build run clean

MAIN_CLASS=FeedReaderMain
MAVEN=mvn
HEURISTIC=q

all: build run

build:
	$(MAVEN) compile

run:
	$(MAVEN) exec:java -Dexec.mainClass="$(MAIN_CLASS)"

run-heuristic:
	$(MAVEN) exec:java -Dexec.mainClass="$(MAIN_CLASS)" -Dexec.args="-ne=$(HEURISTIC)"

clean:
	$(MAVEN) clean
