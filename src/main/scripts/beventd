#!/bin/sh

# Setup variables
EXEC=/usr/bin/jsvc
#JAVA_HOME=/usr/lib/jvm/java-6-sun

export LIB=$BOUNDARY_SDK_HOME/target/lib
CLASS_PATH=$(JARS=("$LIB"/*.jar); IFS=:; echo "${JARS[*]}")
CLASS_PATH="${BOUNDARY_SDK_HOME}/target/boundary-event-sdk-${BOUNDARY_SDK_VERSION}.jar:${CLASS_PATH}"
#echo $CLASS_PATH

CLASS=com.boundary.sdk.event.BoundaryEventDaemon
USER=davidg
#USER=boundary
PID=${BOUNDARY_SDK_HOME}/runtime/beventd.pid
LOG_OUT=${BOUNDARY_SDK_HOME}/logs/beventd.log
LOG_ERR=${BOUNDARY_SDK_HOME}/logs/beventd.err.log
LOG_ERR="&1"

do_exec()
{
    $EXEC -home "$JAVA_HOME" -cp "$CLASS_PATH" -user "$USER" -server -Dboundary.application.context.uri="file:$BOUNDARY_SDK_HOME/src/main/resources/META-INF/event-application.xml" -outfile "$LOG_OUT" -errfile "$LOG_ERR" -pidfile "$PID" $1 "$CLASS"
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
