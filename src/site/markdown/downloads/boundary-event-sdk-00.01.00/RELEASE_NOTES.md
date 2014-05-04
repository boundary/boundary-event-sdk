Release Notes
=============

Boundary Event SDK version 00.01.00
-----------------------------------
 
### Release Features

This is the inagural release of the Boundary Event SDK that includes the ability to receive syslog messages and snmp traps. The Boundary Event SDK will be foundation for future event integrations to the Boundary platform.

#### Syslog Event Adapter
Enables the ability to handle Syslog messages in the [RFC 3164](http://www.ietf.org/rfc/rfc3164.txt) format and then translate the message into Boundary events. The Boundary syslog event adapter requires that the syslog daemon on a host be configured to forward all or a subset of the syslog messages to the UDP listener of the event adapter.

#### SNMP Event Adapter
Enables the ability to receive version 1 and 2c SNMP traps and then translate these traps to Boundary events. The Boundary Event adapter translates OIDs using the SNMP4J-SMI library. Translation of trap from the enterprise sub-branch required a purchasing a license from the [SNMP4J Website](http://www.snmp4j.org/html/buy.html).
 
### Known Issues and Problems

#### Customizing Mapping of Syslog Severity to Event Severity and Status Requires Building
For this release to customization of the Syslog message severity to Boundary event severity, and Syslog message severity to Boundary event status requires rebuilding the distribution. Property files which contain the configuration are stored in the Boundary Event SDK jar and the Syslog event adapter accesses these at runtime rather than those in BOUNDARY_SDK_HOME/etc/syslog.

##### Workaround 
Change the syslog.severity.properties or the syslog.status.properties property files to the mapping that is preferred and then rebuild and package. NOTE: This process is automated so the effort is minimal.

