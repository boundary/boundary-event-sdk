Boundary Event SDK Distribution File System Layout
-------------------------------------------------

Description of the distribution layout of a distribution.

Environment Variables
---------------------

BOUNDARY_SDK_APP_DIR - Directory that contains the application JAR file(s)
BOUNDARY_SDK_ETC_DIR - Directory that contains all of the configuration
BOUNDARY_SDK_JAR_DIR - Directory with all the jars the SDK is dependent upon
BOUNDARY_SDK_LOG_DIR - Directory to write the standard out and standard error logs
BOUNDARY_SDK_PID_DIR - Directory to write the process Id when running as a daemon

Tar or Zip Archive
------------------

```
boundary-event-sdk
   +   boundary-event-sdk-XX.YY.ZZ.jar
   +-- bin
       + bevent
       + beventd
       + beventapp
       + bmibc
       + send-syslog
       + send-v1-trap
       + send-v2c-trap
   +-- docs
       +--apidoc
       +--site
   +-- etc
       + boundary-event-sdk
       + event-routes.xml
   +-- lib
       + <dependent jar files>
   +-- log
       + boundary-event-sdk.log
```

RHEL/CentOS RPM
---------------

```
/
  +-- usr/bin
      + bevent
      + beventd
      + beventapp
      + bmibc
      + send-syslog
      + send-v1-trap
      + send-v2c-trap
  +-- etc/besdk
      + besdk.conf
      + event-routes.xml
  +-- etc/init.d
      + beventd
  +-- usr/share/besdk
      + <dependent jar files>
  +-- var/run
      + beventd.pid 
  +-- usr/share/man/man1
      + beventd.1.gz
 
