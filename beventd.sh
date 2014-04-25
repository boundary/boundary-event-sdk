#!/bin/sh

# Setup variables
EXEC=/usr/bin/jsvc
#JAVA_HOME=/usr/lib/jvm/java-6-sun
CLASS_PATH="$PWD/target/lib/commons-daemon-1.0.15.jar":"$PWD/target/tutorial-1.0-SNAPSHOT.jar"
CLASS=com.boundary.sdk.BoundaryEventDaemon
USER=davidg
USER=boundary
PID=/var/run/beventd.pid
LOG_OUT=$BOUNDARY_SDK_HOME/logs/beventd.log
LOG_ERR=$BOUNDARY_SDK_HOME/logs/beventd.err.log
LOG_ERR="&1"

do_exec()
{
    $EXEC -home "$JAVA_HOME" -cp "$CLASS_PATH" -user "$USER" -server -outfile "$LOG_OUT" -errfile "$LOG_ERR" -pidfile "$PID" $1 "$CLASS"
}

case "$1" in
    start)
        do_exec
            ;;
    stop)
        do_exec "-stop"
            ;;
    restart)
        if [ -f "$PID" ]; then
            do_exec "-stop"
            do_exec
        else
            echo "service not running, will do nothing"
            exit 1
        fi
            ;;
    *)
            echo "usage: daemon {start|stop|restart}" >&2
            exit 3
            ;;
esac
