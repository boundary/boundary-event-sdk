package com.boundary.sdk;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class XMLRoute extends RouteBuilder {

	public XMLRoute() {
	}
	
	@Override
	public void configure() {
		//		from("mina:udp://localhost:10514?sync=false")

		from("mina:udp://localhost:10514")
		.to("file://?fileName=syslog.log")
				.process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received log: " + exchange.getIn().getBody(String.class));
                        Message m = exchange.getIn();
                        Object o = m.getBody();
                        System.out.println("Class: " + o.getClass());
                        Map <String,Object> headers = m.getHeaders();
                        System.out.println("headers: " + headers);
                    }
                })

		//.to("direct:event")
		;
	}
}
