all: compilation

compilation: 
	cd src/ && javacc Compilateur.jj && javac *.java -d ../bin/ && javadoc mirelac/*.java -d ../javadoc/ && cd mirelac/ && javac *.java -d ../../bin/

clean:
	rm -rf bin/* && rm src/*.java