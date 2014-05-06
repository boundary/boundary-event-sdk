Boundary Syslog Event Adapter
=============================

The Syslog adapter for Boundary enables the UDP receipt of syslog messages forwarded from a syslog daemon into Boundary events.

This adapters adheres to standard set forth in [RFC 3164](http://tools.ietf.org/html/rfc3164).

Configuration
-------------

### Forwarding Syslog Messages

The Syslog Event Adapter requires that syslog messages be forwarded as:

* Using the `UDP` transport
* Send in the RFC 3164 format.

There are various open source and commericial implementations of syslog including:

* `syslogd`
* [Kiwi Syslog](http://www.kiwisyslog.com)
* [RSYSLOG](http://www.rsyslog.com)
* [syslog-ng](http://www.syslog-ng.org)

Those list above have the capability to forward syslog messages using UDP and syslog format (RFC 3164).

The configuration to forward syslog messages typically in this format:

```
*.*     @<hostname>:<port>

```

NOTE: Different implementations have different names for the configuration file location (typically /etc/syslog.conf or /etc/rsylog.conf) so
consult the documentation for your implementation.

For example if the Boundary Event SDK was running on the host `ren.stimpy.com` and the Syslog adapter parameter `port` was configured to _1514_
then the typical syslog configuration to forward _all_ of the syslog messages would be the following:

```
*.*    @ren.stimpy.com:1514
```

### Parameters

These parameters that application in the `etc/event-application.xml` file.

* `port` - Port number to listen for Syslog message (default is 1514).
* `routeId` - Name of the route instance appears in logs.
* `startOrder` - Ordering of when this route is started in relationship to other routes.
* `toUri` - Indicates the end point to send the transformed syslog message.

### Example Configuration
```
        <bean id="syslog-route" class="com.boundary.sdk.event.syslog.SysLogRouteBuilder">
                <property name="routeId" value="SYSLOG"/>
                <property name="startUpOrder" value="120"/>
                <property name="port" value="1514"/>
                <property name="toUri" value="seda:boundary-event"/>
        </bean>
```

Event Mapping
----------------------------------------
This section describes the mapping of the Syslog message to a Boundary event.

A syslog message consists of the following fields:

* Facility
* Hostname/IP Address
* Severity
* Message
* Timestamp

### Field Mapping

The table below provides a guide of how the Syslog message fields are mapped to a Boundary event.

|Syslog Field  |Boundary Event Field     |Boundary Field Type|Boundary Fingerprint Field?|Boundary Tag?|
|--------------|:-----------------------:|:-----------------:|:-------------------------:|:-----------:|
|facility      | facility                | property          | YES                       | YES         |
|timestamp     | createdAt               | standard/property | NO                        | NO          |
|message       | message                 | standard/property | YES                       | NO          |
|hostname      | source.ref              | standard/property | YES                       | YES         |
|remote_address| remote_address          | property          | NO                        | YES         |
|severity      | severity(mapped)        | standard          | NO                        | NO          |
|severity      | status(mapped)          | standard          | NO                        | NO          |
|              | title (text + hostname) | standard          | NO                        | NO          |


### Severity Mapping
Mapping of Syslog severity to Boundary event severity is given by the table below. Mapping can be customized by modification of a
java property file (`syslog.severity.properties`).

|Syslog Severity|Boundary Event Severity|
|---------------|-----------------------|
|EMERG          |CRITICAL               |
|ALERT          |CRITICAL               |
|CRIT           |CRITICAL               |
|ERR            |ERROR                  |
|WARNING        |WARNING                |
|NOTICE         |INFO                   |
|INFO           |INFO                   |
|DEBUG          |INFO                   |

### Status Mapping
Boundary event status of a syslog message is determined by the Syslog severity. The table below shows the default mapping, which  customized by modification of a
java property file (`syslog.status.properties`).

|Syslog Severity|Boundary Event Status|
|---------------|---------------------|
|EMERG          |OPEN                 |
|ALERT          |OPEN                 |
|CRIT           |OPEN                 |
|ERR            |OPEN                 |
|WARNING        |OPEN                 |
|NOTICE         |OK                   |
|INFO           |OK                   |
|DEBUG          |OK                   |

Future Enhancements
-------------------
* Generalized mapping and transformation of Syslog message fields to Boundary event fields
* Support for syslog format as specified by [RFC 5424](http://tools.ietf.org/html/rfc5424)


