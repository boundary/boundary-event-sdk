// Extract our object
var sm = request.getBody();
var SyslogFacility = Java.type('org.apache.camel.component.syslog.SyslogFacility');
var SyslogSeverity = Java.type('org.apache.camel.component.syslog.SyslogSeverity');

function getEventSeverity(severity) {
	var eventSeverity;
	
	if (severity == "EMERG" ||
		severity == "ALERT" ||
		severity == "CRIT") {
		eventSeverity = "CRITICAL";
	} else if (severity == "ERR") {
		eventSeverity = "ERROR";
	} else if (severity == "WARNING") {
		eventSeverity = "WARN";
	} else if (severity == "NOTICE" ||
			   severity == "INFO" ||
			   severity == "DEBUG") {
		eventSeverity = "INFO";
	} else {
		print("Unknown severity")
		eventSeverity = "UNKNOWN";
	}
	
	return eventSeverity;
}

function getEventStatus(severity) {
	var eventStatus;
	
	if (severity == "EMERG" ||
		severity == "ALERT" ||
		severity == "CRIT"  ||
		severity == "ERR"   ||
		severity == "WARNING") {
		eventStatus = "OPEN";
	} else if (severity == "NOTICE" ||
			   severity == "INFO"   ||
			   severity == "DEBUG") {
		eventStatus = "OK";
	}
	else {
		print("Unknown severity")
		eventStatus = "UNKNOWN";
	}
	
	return eventStatus;
}

// Create a new event
var e = new com.boundary.sdk.event.RawEvent();

// Add facility
e.getProperties().put("facility",sm.getFacility());
e.addTag(sm.getFacility());

// Add the host name
e.getSource().setRef(sm.getHostname()).setType("host");
e.getProperties().put("hostname", sm.getHostname());
e.addTag(sm.getHostname());

// Add the message
e.setMessage(sm.getLogMessage());
e.getProperties().put("message", sm.getLogMessage());

// Add the remote address
e.getProperties().put("remote_address", sm.getRemoteAddress());
e.addTag(sm.getRemoteAddress());

// Map the syslog severity to Boundary event severity
var severity = getEventSeverity(sm.getSeverity());
e.setSeverity(severity);

// Set event status based on Severity
var status = getEventStatus(sm.getSeverity());
e.setStatus(status);

// Set the uniqueness of the event by hostname, facility, and message.
e.addFingerprintField("hostname");
e.addFingerprintField("facility");
e.addFingerprintField("message");

// Set the time at which the Syslog record was created
e.setCreatedAt(sm.getTimestamp());

// Set Title
e.setTitle("Syslog message from: " + sm.getHostname());

// Set Sender
e.getSender().setRef("Syslog");
e.getSender().setType("Boundary Event SDK");

result = e;