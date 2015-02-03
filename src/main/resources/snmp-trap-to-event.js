// Extract the SNMP Message
var snmpMessage = request.getBody();
print(snmpMessage.getClass().getName());

var PDU = Java.type('org.snmp4j.PDU');

function processV1Trap() {
	
}

function processV2Trap() {
	
}

// Create a new event
var event = com.boundary.sdk.event.RawEvent.getDefaultEvent();
print(event.getTitle());

// Extract the PDU
var pdu = snmpMessage.getSnmpMessage();

// Process differently based on the type of trap
if (pdu.getType() == PDU.V1TRAP) {
	processV1Trap(snmpMessage, pdu, event);
}
else {
	processV2Trap(snmpMessage, pdu, event);
}

// Return our event
result = event;
