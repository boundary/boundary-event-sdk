Building
========
This page provides instructions on how to build the Boundary Event SDK.

Prerequisites
-------------
* Maven 3.2.1 or later
* Java JDK 1.7
* gcc 4.2.1 or later
* Git 1.7.1 or later


Instructions
------------

### Building the software

1. Clone the distribution

    ```$ git clone XXX```    

2. Change directory to the cloned repository

    ```$ cd boundary-event-sdk```

3. Install additional components to build the distribution by running:

    ```$ bash setup.sh```

4. Source the environment

    ```$ source env.sh```

5. Build and package the distribution

    ```$ mvn assembly:assembly```

### Running the Boundary Event SDK in development

### Building the documentation

1. Issue command to create the documentation:

    ```$ mvn site```

2. 

Boundary Event SDK
==================
Boundary Event SDK enables the rapid integration of foreign events into the Boundary event management platform.


1. Set Boundary Organization and API key environmentvariables
2. Run the following: ```mvn camel:run ```

### Stopping Camel
1. Control-c in the console where Camel is running

## Syslog Configuration

### Forwarding Messages from Syslog:

Forwarding of syslog messages to another host is controlled by configuration set in the `/etc/syslog.conf` configuration file or
`/etc/rsyslog.conf` in the case of the `rsyslog` implementation. Examples are provided below that describe this configuration.

To configure syslog to forward messages to another host via UDP, prepend the hostname with the at sign ("@").

Example:

```
*.info @192.168.0.1:1514
```
In the example above, all messages with severity of `info` are forwarded via UDP to the machine 192.168.0.1 on port 1514.

```
daemon.*          @127.0.0.1:10514
```

In this example, all messages with a facility of `daemon` are forwarded to the localhost(127.0.0.1) on port 10514.

```
local0.info          @172.16.0.1:1514
```

In this last example, all messages with a facility of `local0` and `info` severity are forwarded to the host 172.16.0.1:1514


A license can be purchased SNMP4J-SMI from [http://www.snmp4j.org](http://www.snmp4j.org)

## SMI Tests
JUnit tests for testing OID lookup require that the environment variable SNMP4J_LICENSE
be set to license key you purchase otherwise tests that reference the branch _iso.org.dod.internet.private.enterprises_
or _1.3.6.1.4.1.9._ will be skipped.

`SNMP4J_MIB_REPOSITORY` environment variables points to the directorly with the compiled MIBs

## Commons Daemon

[https://issues.apache.org/jira/browse/DAEMON-281](https://issues.apache.org/jira/browse/DAEMON-281)

[![Build Status](https://travis-ci.org/boundary/boundary-event-sdk.svg?branch=master)](https://travis-ci.org/boundary/boundary-event-sdk)
