package com.boundary.sdk;

import java.util.Date;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

public class BoundaryTimerRoute extends RouteBuilder {
	
	private int delay=2000;
	
	public BoundaryTimerRoute(int delay) {
		this.delay = delay;
	}

	@Override
	public void configure() throws Exception {
		from("timer:boundary?delay=" + this.delay).process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				System.out.println(this.getClass().getName() + ": Invoked timer at " + new Date());
			}
		}).beanRef("boundary")
//		.to("direct:event")
		;
	}
}