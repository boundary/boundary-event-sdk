Boundary Event API Route
========================

This route encapsulates requests to send a `RawEvent`s to Boundary Event Host 
via REST call with a JSON payload of the field contents.

Configuration
-------------

### Environment Variables

Boundary Event API configurat parameters API Key and Organization ID are set
by setting the environmen  and `license` can be set
by providing values in the `BOUNDARY_SDK_HOME/etc/boundary-event-sdk` file.

### Parameters

These parameters are configured in the `BOUNDARY_SDK_HOME/event-application.xml`.

* `apiHost` - Boundary Event API host (default: `api.boundary.com`)
* `apiKey` - Boundary Event API Key 
* `orgId` - Boundary Organization ID
* `routeId` - Name to identify the route in the logs
* `startOrder` - Ordering of when this route is started in relationship to other routes
* `fromUri` - Indicates the end point to receive Boundary Event Raw Events. Additionally it configures the number of threads to process events

### Example Configuration
```
<!-- Definition of the route that communicates with the Boundary API host -->
<bean id="boundary-event-route" class="com.boundary.sdk.event.BoundaryEventRouteBuilder">
	<property name="routeId" value="BOUNDARY-EVENT"/>
	<property name="startUpOrder" value="100"/>
	<property name="orgId" value="#{systemEnvironment['BOUNDARY_ORG_ID']}"/>
	<property name="apiKey" value="#{systemEnvironment['BOUNDARY_API_KEY']}"/>
	<property name="fromUri" value="seda:boundary-event?concurrentConsumers=10"/>
</bean>
```
