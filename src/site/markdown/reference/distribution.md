Boundary Event SDK Distribution File System Layout
-------------------------------------------------

Description of the distribution layout of a distribution.

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
 
