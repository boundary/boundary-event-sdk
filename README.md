Boundary Event SDK
==================


# Configuration

## Boundary Configuration

The Boundary organization id and API keys is set by setting the following environment variables:

* `BOUNDARY_ORG_ID`
* `BOUNDARY_API_KEY`

in the `bsdk-env.sh`

## Running Camel

### Starting Camel


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

## SNMP Configuration


# Building


# Install SNMP4J

1. Execute the boot strap script: ```$ bash boostrap.sh```

# SNMP4J License Configuration
Boundary SDK uses the SNMP4J-SMI library for translating OIDs to strings. The open source version is not able to translate OIDs
in the enterprise branch: _iso.org.dod.internet.private.enterprises_

A license can be purchased SNMP4J-SMI from [http://www.snmp4j.org](http://www.snmp4j.org)

## SMI Tests
JUnit tests for testing OID lookup require that the environment variable SNMP4J_LICENSE
be set to license key you purchase otherwise tests that reference the branch _iso.org.dod.internet.private.enterprises_
or _1.3.6.1.4.1.9._ will be skipped.

`SNMP4J_MIB_REPOSITORY` environment variables points to the directorly with the compiled MIBs

## Commons Daemon

[https://issues.apache.org/jira/browse/DAEMON-281](https://issues.apache.org/jira/browse/DAEMON-281)

