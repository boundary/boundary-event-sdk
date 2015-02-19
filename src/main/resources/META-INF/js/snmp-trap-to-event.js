	// Copyright 2014-2015 Boundary, Inc.
	//
	// Licensed under the Apache License, Version 2.0 (the "License");
	// you may not use this file except in compliance with the License.
	// You may obtain a copy of the License at
	//
	//     http://www.apache.org/licenses/LICENSE-2.0
	//
	// Unless required by applicable law or agreed to in writing, software
	// distributed under the License is distributed on an "AS IS" BASIS,
	// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	// See the License for the specific language governing permissions and
	// limitations under the License.

load("src/main/resources/META-INF/js/snmp-library.js");

// Get out SnmpTrap instance, which contains information received from
// the SNMP Trap.
var trap = body;

// SnmpTrap is instance of a java object with the following properties
// Host
// Variable Bindings
// Snmp Version
// Raw

// Create a new raw event to populated with SNMP trap information

// A Raw Event consists of the following fields
// create
event = new com.boundary.sdk.event.RawEvent();

var snmp = new Snmp();

function trapToEvent(trap,event) {
	var trapName = trap.getTrapName();
	
	// createdAt
	var now = new java.util.Date();
	event.setCreatedAt(now);
	
	// fingerprintFields and properties
	
	var varBinds = trap.getVariableBindings();
	
	for each (var vb in varBinds) {
		var oid = vb.getOid();
		var obj = snmp.findObjectByOid(oid);
		if (obj) {
			var objectName = obj.getObjectName();
			event.addProperty(objectName,vb.toValueString());
			event.addFingerprintField(objectName);
		}
		else {
			event.addProperty(oid.toString(),vb.toValueString());
			event.addFingerprintField(oid.toString());
		}
	}
	
	// message
	event.setMessage("Received " + trap.getTrapName() + " trap");
	
	// organization id, Set upon receipt by Boundary

	// receivedAt, set upon receipt within Boundary
	
	// Source
	var source = new com.boundary.sdk.event.Source();
	source.setRef(trap.getHost());
	source.setType("host");
	event.setSource(source);

	// sender
	var sender = new com.boundary.sdk.event.Source();
	sender.setRef("Boundary Event SDK");
	sender.setType("Boundary Event SDK");
	event.setSender(sender);

	// severity
	event.setSeverity(SEVERITY.WARN);

	// status
	event.setStatus(STATUS.OPEN);

	// tags
	event.addTag(trap.getHost());
	event.addTag(trap.getTrapName());
	
	// Examine our trap instance to see if it was processed adequately
	// if not then tag the event as raw so the trap can be configured
	// to be processed properly
	if (trap.isRaw()) {
		event.addTag("raw");
	}

	// title
	event.setTitle(trap.getTrapName() + " trap received from " + trap.getHost());
}

// Call our function to translate the event
trapToEvent(trap,event);

// Set the return event
result = event;