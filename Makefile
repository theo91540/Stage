all: compilation

compilation: 
	cd src/ && javacc Compilateur.jj && javac *.java -d ../bin/ && cd mirelac/ && javac *.java -d ../../bin/

clean:
	rm -f bin/*