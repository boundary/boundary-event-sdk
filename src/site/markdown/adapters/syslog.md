Boundary Syslog Event Adapter
=============================

Overview
--------
The Syslog adapter for Boundary enables the UDP receipt of syslog messages forwarded from a syslog daemon into Boundary events.


The [SNMPRouteBuilder](http://www.google.com) is responsible for generating a [camel route] and translating into a [`RawEvent`](https://app.boundary.com/docs/events_api#RawEvent)

Configuration
------------

### Parameters

* `port` - Port number to listen for Syslog message (default is 1514)
* `routeId` - Name of the
* `startOrder` - Ordering of when this route is started in relationship to other routes
* `toUri` - Indicates the end point to send the transformed syslog message

### Example Configuration
```
        <bean id="syslog-route" class="com.boundary.sdk.event.syslog.SysLogRouteBuilder">
                <property name="routeId" value="SYSLOG"/>
                <property name="startUpOrder" value="120"/>
                <property name="port" value="1514"/>
                <property name="toUri" value="seda:boundary-event"/>
        </bean>

```

Syslog Message to Boundary Event Mapping
----------------------------------------
This section describes the mapping of the Syslog message to a Boundary event.

A syslog message consists of the following fields:

* Facility
* Hostname/IP Address
* Severity
* Message
* Timestamp

#### Field Mapping

* properties
** facility
** hostname

```
	private void syslogMessageToEvent(SyslogMessage sm, RawEvent e) {

		e.getProperties().put("facility", sm.getFacility());
		e.putTag(sm.getFacility().toString());
		
		// Add the hostname
		e.getSource().setRef(sm.getHostname()).setType("host");
		e.getProperties().put("hostname", sm.getHostname());
		e.putTag(sm.getHostname());
		
		// Add the message
		e.setMessage(sm.getLogMessage());
		e.getProperties().put("message", sm.getLogMessage());
		
		// Add the remote address
		e.getProperties().put("remote_address", sm.getRemoteAddress());
		e.putTag(sm.getRemoteAddress());
		
		// Map the syslog severity to Boundary event severity
		Severity severity = getEventSeverity(sm.getSeverity());
		e.setSeverity(severity);
		e.putProperty("severity", sm.getSeverity().toString());
		e.putTag(sm.getSeverity().toString());
		
		Status status = getEventStatus(sm.getSeverity());
		e.setStatus(status);
		
		// Set the uniqueness of the event by hostname and facility
		// TBD These fields need to be split out in a configuration file
		e.putFingerprintField("hostname");
		e.putFingerprintField("facility");
		
		// Set the time at which the syslog record was created
		// TBD: Ensure time is in UTC
		e.setCreatedAt(sm.getTimestamp());
		e.putProperty("timestamp", sm.getTimestamp());

		// Set Title
		// TBD External configuration
		e.setTitle("Syslog Message from  " + sm.getHostname());
	}
```
### Severity Mapping
Mapping of Syslog severity to Boundary event severity is a one on one
basis give by the table below. Mapping can be customized by modification of a
java property file.

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


