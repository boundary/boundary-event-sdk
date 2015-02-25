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

var SCRIPT_DIR=java.lang.System.getenv("BOUNDARY_SDK_SCRIPT_DIR");

try {
	load(SCRIPT_DIR + "/lib/notification.js");
} catch(err) {
	print("Unable to load notification javascript library");
	print("Ensure that the BOUNDARY_SDK_SCRIPT_DIR environment variable is set")
}


// Extract the notification object
var notification = body;
var execList = new com.boundary.sdk.event.exec.ExecList();

// We are going call a program if the notification is in TRIGGERED STATUS
if (notification.getStatus() == NOTIFICATION_STATUS.TRIGGERED) {
	var servers = notification.getAffectedServers();
//	var len = servers.size();
//		
//	for (; i < len; i++) {
//		var server = servers.get(i);
//		var exec = new com.boundary.sdk.event.Exec("echo","Alarm for server: " + server.getHostname());
//		execList.add(exec);
//	}
	
//	for each (var e in map.keySet()) print(e);  // foo, bar
//
	for each (var server in servers.values()) {
		var exec = new com.boundary.sdk.event.exec.Exec("echo","Alarm for server: " + server.getHostname());
		execList.add(exec);

	}
}

result = execList;

