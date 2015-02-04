
// Extract the body from the request message
var entry = request.getBody();

var measure = new com.boundary.sdk.metric.Measurement()

// Build Date of 2014-12-31 13:13:13
var builder = new  java.util.Calendar.Builder();
builder.setDate(2014,11,31);
builder.setTimeOfDay(13,13,13);
var timestamp = builder.build().getTime();


measure.metric = entry.getOid();
measure.measure = entry.getValue();
measure.source = "SNMP";
measure.timestamp = timestamp;

result = measure

