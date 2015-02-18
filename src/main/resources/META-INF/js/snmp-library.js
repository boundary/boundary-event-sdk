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

// Extract

function Snmp() {
	var BOUNDARY_SMI_MANAGER = com.boundary.sdk.event.snmp.SnmpMessageToVarBinds.BOUNDARY_SMI_MANAGER;
	this.smi = request.getHeader(BOUNDARY_SMI_MANAGER);

	this.findObjectByOid = function(oid) {
		var smiManager = this.getSmiManager();
		return smiManager.findSmiObject(oid);
	};
	
	this.getSmiManager = function() {
		if (this.smi === null) {
		}
		return this.smi;
	}
}


