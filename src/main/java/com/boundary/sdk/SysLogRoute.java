package com.boundary.sdk;

import org.apache.camel.builder.RouteBuilder;

public class SysLogRoute extends RouteBuilder {

	public SysLogRoute() {
	}
	
	@Override
	public void configure() {
		//		from("mina:udp://localhost:10514?sync=false")

		from("mina:udp://localhost:10514")
		.to("file://?fileName=syslog.log")
		//.to("direct:event")
		;
	}
}
