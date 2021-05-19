RUN ON LINUX:

$javac main*/*.java
$jar cvfm blackjack.jar main/manifest.txt main/*.class blackjack/*.class cards/*.class player/*.class
$java -jar blackjack.jar -i 5 50 500 8 60