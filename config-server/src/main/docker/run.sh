echo "********************************************************"
echo "Waiting for the service discovery to start on port 8080"
echo "********************************************************"
while ! `nc -z 172.32.0.105 8080 `; do sleep 3; done
echo ">>>>>>>>>>>> Service Discovery has started"

#!/bin/sh
java -Dserver.port=$SERVER_PORT \
     -Dfile.encoding=UTF-8       \
     -jar /usr/local/config-server/@project.build.finalName@.jar
