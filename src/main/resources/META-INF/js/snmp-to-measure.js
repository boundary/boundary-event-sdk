
// Extract the bind variable
var varBind = body;

var oid = varBind.getOid();

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
var oidDef = map.get(oid.toDottedString());
measure.metric = oidDef.getMetricId();

/*
 * Measure
 */
measure.measure = varBind.getVariable().getValue();

/*
 * Source
 */
measure.source = config.getHost();

/*
 * Timestamp
 */
// The default constructor for Measurement set the timestamp to "now"

result = measure

