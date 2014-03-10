#!/bin/bash

#
# Execute our Java Application with root priviledges since we 
# have to bind to a well known port 161 for the SNMP trap handler
#
mvn exec:java -Dexec.mainClass="com.boundary.sdk.BoundaryIntegrationApp"
