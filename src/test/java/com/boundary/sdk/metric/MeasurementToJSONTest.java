package com.boundary.sdk.metric;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MeasurementToJSONTest extends CamelSpringTestSupport {
	
	private final String DEFAULT_JSON="{\"source\":\"\",\"metric\":\"\",\"measure\":0}";
	
    @Produce(uri = "direct:measure-in")
    private ProducerTemplate producerTemplate;
    
    @EndpointInject(uri = "mock:json-out")
    private MockEndpoint mockOut;

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/measure-to-json-test.xml");
	}

	@Test
	public void testSetSource() throws Exception {
		mockOut.setExpectedMessageCount(1);
		Measurement measurement = new Measurement();
		
		measurement.setSource("1");	
		producerTemplate.sendBody(measurement);
		assertMockEndpointsSatisfied();
	}
	
	@Test
	public void testDefaultJSON() throws Exception {
		mockOut.setExpectedMessageCount(1);
		Measurement measurement = new Measurement();
		
		producerTemplate.sendBody(measurement);
		
		List<Exchange> exchanges = mockOut.getExchanges();
		
		for (Exchange exchange : exchanges) {
			assertEquals("check for default JSON",DEFAULT_JSON,exchange.getIn().getBody(String.class));
		}
	}
	
	@Test
	public void testPayload() {
		
	}
}
