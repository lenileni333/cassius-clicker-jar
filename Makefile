make:
	clear
	rm -f *.class; rm -f utils/*.class;rm -f utils/classesandenums/*.class; rm -f utils/data/*.class
	rm -f game/*.class
	sleep 1
	javac Main.java
	java Main