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

// Definition of the header that is stored in the Camel exchange


// References to get access to the enumerations for event severity and status
var SEVERITY = Java.type('com.boundary.sdk.event.Severity');
var STATUS = Java.type('com.boundary.sdk.event.Status');
var SNMP_VERSION = Java.type('com.boundary.sdk.event.snmp.SnmpTrap.SnmpVersion');

// Define a JavaScript object to interface to SNMP4J in java

function Snmp() {
	var BOUNDARY_SMI_MANAGER = com.boundary.sdk.event.snmp.SnmpMessageToVarBinds.BOUNDARY_SMI_MANAGER;
	var snmpScript = new com.boundary.sdk.event.snmp.SnmpScript();
	snmpScript.load();
	this.smiManager = snmpScript.getSmiManager();
	
	this.findObjectByOid = function(oid) {
		return this.smiManager.findSmiObject(oid);
	};
	
	this.getScriptDirectory = function() {
		return java.lang.System.getenv("BOUNDARY_SDK_SCRIPT_DIR");
	};
}


