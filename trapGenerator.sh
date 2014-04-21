#!/bin/bash

mvn -q exec:java -Dexec.mainClass="com.boundary.sdk.event.snmp.SendTrap"
