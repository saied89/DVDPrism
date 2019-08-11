#!/usr/bin/env bash

JAR_FILE_NAME='backend.jar'
REMOTE_USER = 'saied'

echo 'kill $(ps aux | grep '$JAR_FILE_NAME$' | grep -v grep | awk \'{print $2}\')'

scp -v backend/build/libs/${JAR_FILE_NAME} $REMOTE_USER@$SERVER_IP:$JAR_FILE_NAME
#end process
ssh $REMOTE_USER@$SERVER_IP 'kill $(ps aux | grep '$JAR_FILE_NAME$' | grep -v grep | awk \'{print $2}\')'
#run jar
ssh $REMOTE_USER@$SERVER_IP 'nohup java -jar $JAR_FILE_NAME &'