package com.boundary.sdk;

import java.util.Map;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;
/**
 * 
 * @author davidg
 *
 */
public class TestRoute extends RouteBuilder {

	public TestRoute() {
	}
	
	@Override
	public void configure() {
		//RouteBuilder r = new RouteBuilder();
		// body().append(getBody()).
		from("direct:test")
		.unmarshal().serialization()
		.process(new Processor() {
			public void process(Exchange exchange) throws Exception {
            System.out.println("Received message body: " + exchange.getIn().getBody(String.class));
            Message m = exchange.getIn();
            Object o = m.getBody();
            System.out.println("Class: " + o.getClass());
            Map <String,Object> headers = m.getHeaders();
            System.out.println("headers: " + headers);
			}
		})
		.marshal().string()
		.to("file://?fileName=test.log")
		;
	}
}
