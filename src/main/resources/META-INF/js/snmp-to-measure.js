
// Extract the bind variable
var bindVar = body;

// The configuration for this poller is stored in the following header of the message
var config = request.getHeader("boundary.snmp.poller.configuration");
// Retrieve a map of Oid instances keyed by the string representation of the OID
var map = config.getOidMap();

// Create a new Measurement instance to populate
var measure = new com.boundary.sdk.metric.Measurement()


/*
 *  Metric Id
 */
// Lookup the OID for the bind variable
var oid = map.get(bindVar.getOid());
measure.metric = oid.getMetricId();

/*
 * Measure
 */
measure.measure = bindVar.getValue();

/*
 * Source
 */
measure.source = config.getHost();

/*
 * Timestamp
 */

//Build Date of 2014-12-31 13:13:13
var builder = new java.util.Calendar.Builder();
builder.setDate(2014,11,31);
builder.setTimeOfDay(13,13,13);
measure.timestamp = builder.build().getTime();

result = measure

