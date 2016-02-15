JFLAGS = -g
JC = javac
JVM = java
MAIN = Crawler
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Crawler.java \
        Date_Tracker.java \
        Generate_Queries.java \
        Get_Doc.java \
        Get_List_Docs.java \
        Network.java

default: clean classes

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN)

clean:
	$(RM) *.class