Boundary SNMP Event Adapter
===========================

This event adapter can receive v1/v2c SNMP traps and translate into Boundary Events.


SNMP4J-SMI
----------

The SNMP adapter uses the SNMP4J-SMI library to decode OIDs. For open source use free
license is granted which restricts usage to standard MIB modules which are 
not under the enterprise OID sub-tree. Licenses can be purchased from
[www.snmp4j.org](http://www.snmp4j.org/html/buy.html) for, at the time of this writing,
for $49 US.

Configuration
-------------

### Environment Variables

SNMP adapter parameters `mibRepository` and `license` can be set
by providing values in the `BOUNDARY_SDK_HOME/etc/boundary-event-sdk` file.

### Parameters

These parameters are configured in the `BOUNDARY_SDK_HOME/event-application.xml`.

* `routeId` - Name to identify the route in the logs
* `startOrder` - Ordering of when this route is started in relationship to other routes
* `port` - Port number to listen for SNMP traps (default is 1162)
* `mibRepository` - Indicates the end point to send the transformed SNMP trap.
* `license` - License string purchased from SNMP4J-SMI
* `toUri` - Indicates the end point to send the transformed SNMP trap.

### Example Configuration
```
        <!-- This route recieves v1 and v2c SNMP traps -->
        <bean id="snmp-route" class="com.boundary.sdk.event.snmp.SNMPRouteBuilder">
                <property name="routeId" value="SNMP"/>
                <property name="startUpOrder" value="130"/>
                <property name="port" value="1162"/>
                <property name="mibRepository" value="#{systemEnvironment['BOUNDARY_MIB_REPOSITORY']}"/>
                <property name="license" value="#{systemEnvironment['BOUNDARY_MIB_LICENSE']}"/>
                <property name="toUri" value="seda:boundary-event"/>
        </bean>
```

### Event Mapping
This section describes the mapping of the SNMP trap to a Boundary event.

A SNMP v1 trap consists of the following fields:

* Facility
* Hostname/IP Address
* Severity
* Message
* Timestamp

A SNMP v2 trap consists of list of varbinds:
* varbind 1
* varbind 2


### Field Mapping


### References

* Mauro, Douglas R.; Schmidt, Kevin J.  _Essential SNMP_,2nd Ed. California: Sebastopol, OReily Media,Inc., 2009. Print.

### Future Enhancements
* Generalized mapping and transformation of SNMP trap varbinds message fields to Boundary event fields
* Support for perform SNMP gets.


