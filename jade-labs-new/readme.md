mvn -Pjade-main exec:java
mvn -Pjade-agent exec:java
D:\fils\aose\aose\jade-labs-new\target\classes>java -cp .;..\..\lib\jade.jar jade.Boot -agents fred:com.btesila.aose.jade.labs.HelloAgent
D:\fils\aose\aose\jade-labs-new\target\classes>java -cp .;..\..\lib\jade.jar jade.Boot -agents "fred:com.btesila.aose.jade.labs.Exercise2(1, 2)"


java -cp .;..\..\lib\jade.jar jade.Boot -agents main:com.btesila.aose.jade.labs.Sender a1:com.btesila.aose.jade.labs.Receiver
>java -cp .;..\..\lib\jade.jar jade.Boot -container main:com.btesila.aose.jade.labs.Sender


