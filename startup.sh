#!/bin/sh
export JAVA_HOME="/home/wuser/jdk1.8.0_121"
export JRE_HOME="/home/wuser/jdk1.8.0_121/jre"
nohup /home/wuser/jdk1.8.0_121/bin/java -jar /opt/webapps/jdspider/lib/jdspider.jar --spring.config.location=/opt/config/jdspider/conf/application.properties > /opt/webapps/jdspider/nohup.log &
