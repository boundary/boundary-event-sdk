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
// Extract the bind variable
var varBind = body;
var oid = varBind.getOid();

// The configuration for this poller is stored in the following header of the message
var config = request.getHeader("boundary.snmp.poller.configuration");

// Retrieve a map of Oid instances keyed by the string representation of the OID
var map = config.getOidMap();

// Create a new Measurement instance to populate
var measure = new com.boundary.sdk.metric.Measurement()

// METRIC ID - Lookup the OID for the bind variable from our oid map
var oidDef = map.get(oid.toDottedString());
measure.metric = oidDef.getMetricId();

// MEASURE - Extract from the varible binding
measure.measure = varBind.getVariable().getValue();

// SOURCE
measure.source = config.getHost();

// TIMESTAMP - Get the current time
measure.timestamp = new java.util.Date();

// Return our result
result = measure

