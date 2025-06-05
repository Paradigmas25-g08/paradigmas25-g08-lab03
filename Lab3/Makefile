.PHONY: all build run clean

MAIN_CLASS=FeedReaderMain
MAVEN=mvn
CLASSES=target/classes
HEURISTIC=q
JVM_FLAGS=--add-exports=java.base/sun.nio.ch=ALL-UNNAMED --add-opens=java.base/sun.nio.ch=ALL-UNNAMED

# Obtiene el classpath desde Maven (todos los JARs de dependencias)
CP=$(shell $(MAVEN) dependency:build-classpath -Dmdep.outputFile=cp.txt > /dev/null && cat cp.txt)

all: build run-heuristic

build:
	$(MAVEN) compile

run:
	java $(JVM_FLAGS) -cp "$(CLASSES):$(CP)" $(MAIN_CLASS)

run-heuristic:
	java $(JVM_FLAGS) -cp "$(CLASSES):$(CP)" $(MAIN_CLASS) -ne=$(HEURISTIC)

clean:
	$(MAVEN) clean
	rm -f cp.txt