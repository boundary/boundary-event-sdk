#!/bin/bash

mvn exec:java -Dexec.mainClass="com.boundary.sdk.EventApplication" -Dboundary.orgid=${BOUNDARY_ORG_ID} -Dboundary.apikey=${BOUNDARY_API_KEY}
