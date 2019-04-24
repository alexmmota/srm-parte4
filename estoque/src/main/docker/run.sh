#!/bin/sh

echo "********************************************************"
echo "Waiting for the database server to start on port 3306"
echo "********************************************************"
while ! `nc -z database 3306`; do sleep 3; done
echo ">>>>>>>>>>>> Database Server has started"

echo "********************************************************"
echo "Waiting for the configuration server to start on port 8080"
echo "********************************************************"
while ! `nc -z config-server 8080 `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

echo "********************************************************"
echo "Waiting for the service discovery to start on port 8080"
echo "********************************************************"
while ! `nc -z service-discovery 8080 `; do sleep 3; done
echo ">>>>>>>>>>>> Service Discovery has started"

java -Dserver.port=$SERVER_PORT                                 \
     -Dspring.cloud.config.uri=$CONFIGSERVER_URI                \
     -Dspring.profiles.active=$PROFILE                          \
     -Dfile.encoding=UTF-8       \
     -jar /usr/local/estoque/@project.build.finalName@.jar