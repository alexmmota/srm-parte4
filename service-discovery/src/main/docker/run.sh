#!/bin/sh

java -Dspring.profiles.active=$PROFILE                          \
     -Dfile.encoding=UTF-8                                      \
     -jar /usr/local/service-discovery/@project.build.finalName@.jar