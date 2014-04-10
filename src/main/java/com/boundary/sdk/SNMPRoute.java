package com.boundary.sdk;

import org.apache.camel.builder.RouteBuilder;
/**
 * 
 * @author davidg
 *
 */
public class SNMPRoute extends RouteBuilder {

	public SNMPRoute() {
	}
	
	@Override
	public void configure() {
		//RouteBuilder r = new RouteBuilder();
		// body().append(getBody()).
		from("snmp:127.0.0.1:1162?protocol=udp&type=TRAP")
		.routeId("SNMP-ROUTE")
		.to("file://?fileName=snmptrap.log")
		.process(new SNMPToEventProcessor())
		.marshal().serialization()
		.to("direct:event");
	}
}
