set -v

mvn -DskipTests clean package

# Change the line below to the location of Tomcat built from trunk
TOMCAT=~/dev/sources/apache/tomcat/trunk/output/build

rm -rf $TOMCAT/webapps/spring-websocket-autobahn*

cp target/spring-websocket-autobahn.war $TOMCAT/webapps/

$TOMCAT/bin/shutdown.sh
sleep 3

$TOMCAT/bin/startup.sh
