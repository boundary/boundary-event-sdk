load("snmp-library.js");

var varBinds = body;
var smiManager = getSmiManager();
print(varBinds.getClass().toString())

var event = new com.boundary.sdk.event.RawEvent();


result = event;