package com.boundary.sdk;

import java.io.File;
import java.util.ArrayList;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class QueueRouteTest extends CamelSpringTestSupport {
	
	private static final String IN_URI="seda:testinQueueIn";
	private static final String OUT_URI="mock:testingMockOut";
	
    @Produce(uri = IN_URI)
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = OUT_URI)
    private MockEndpoint mockOut;

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/boundary-event-to-json.xml");
	}
	
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
    	
    	// TBD: Provide shutdown strategy where the thread does not give up
    	CamelContext context = context();

    	QueueRouteBuilder q = new QueueRouteBuilder();
    	
    	q.setFromUri(IN_URI);
    	q.setToUri(OUT_URI);
    	q.setQueueName("TESTING");
 
        return q;
    }

	@Test
	public void testSendOneEvent() throws Exception {
		mockOut.setExpectedMessageCount(10);
		RawEvent event = RawEvent.getDefaultEvent();		
		event.setTitle("TEST EVENT");
		
		for (int i = 10 ; i != 0 ; i--) {
			producerTemplate.sendBody(event);
			Thread.sleep(10);
		}
		


		assertMockEndpointsSatisfied();
	}
}