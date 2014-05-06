User Guide
==========

This document describes how to install and use the Boundary Event SDK to integrate
Syslog and SNMP into your Boundary environment.

Prerequisites
-------------
* Boundary Organizational Id
* Boundary API Key
* License key from [snmp4j](http://www.snmp4j.org/) if you plan on receiving traps from the enterprise sub-branch and wish OIDs to mapped to readable strings.

Installation
------------
1. Download the distribution from [Boundary Event SDK Github](https://github.com/boundary/boundary-event-sdk/dist)
2. Extract the archive:
```$ cd tar xvf boundary-event-sdk-X.YY.ZZ-dist.tar.gz```

Configuration
-------------

### Environment Variables

Boundary SDK requires the following the environment variables be set prior to running:

* `BOUNDARY_SDK_HOME` - Path to the extracted the Boundary SDK distribution
* `BOUNDARY_API_KEY` - Boundary API Key
* `BOUNDARY_ORG_ID` - Boundary Organization ID
* `BOUNDARY_MIB_REPOSITORY` - Stores the compiled MIBs for SNMP trap adapter
* `BOUNDARY_MIB_LICENSE` - License string for SNMP4J-SMI

These environment variables can be either set external or setting directly in environment script `BOUNDARY_SDK_HOME/etc/boundary-event-sdk`.

### Set Runtime Environment

1. Add the required environment variables to the environment.
2. Change directory to the distribution
```$ cd boundary-event-sdk-XX.YY.ZZ```      
3. Source the environment
```$ source etc/boundary-event-sdk```     
4. Verify the environment
```$ benv```

### Stopping the Event Daemon
1. Issue the command:
```$ bin/beventd start```

### Stopping the Event Daemon
1. Issue the command:
```$ bin/eventd stop```

### Log Files
The event daemon(`beventd`) logs output to `BOUNDARY_SDK_HOME/logs/beventd.log

