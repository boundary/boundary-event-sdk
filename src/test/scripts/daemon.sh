#!/bin/bash

START_TIME=15
SLEEP_TIME=60
DAEMON_NAME=$(basename $0)

log() {
  logger -p daemon.notice
}

Main() {
 
  echo "$DAEMON_NAME starting..." | log
  sleep $START_TIME 
  echo "$DAEMON_NAME started" | log

  while :
  do 
     sleep $SLEEP_TIME
     echo "$DAEMON_NAME is working" | log
  done
}


Main $*
