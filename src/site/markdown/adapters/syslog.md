Boundary Syslog Event Adapter
=============================

Overview
--------



Operation
---------


Parameters
----------

* toUri - 

* startupOrder

*

Event Mapping
-------------

# Syslog message to Boundary Event Mapping

## Field Mapping
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
## Severity Mapping

```
# Default configuration for Syslog 

#
# Default mapping of Syslog severity to Boundary severity
#
EMERG: CRITICAL
ALERT: CRITICAL
CRIT: CRITICAL
ERR: ERROR
WARNING: WARN
NOTICE: INFO
INFO: INFO
DEBUG: INFO
```

## Status Mapping

```
# Default configuration for Syslog 

#
# Default mapping of Syslog severity to Boundary status
#
EMERG: OPEN
ALERT: OPEN
CRIT: OPEN
ERR: OPEN
WARNING: OPEN
NOTICE: OK
INFO: OK
DEBUG: OK
```


