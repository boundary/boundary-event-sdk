package com.boundary.sdk;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("deprecation")
public class SyslogToEventJSTest extends CamelSpringTestSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(SyslogToEventJSTest.class);
	
	private static final String SYSLOG_IN = "direct:syslog-in";
	private static final String EVENT_OUT = "mock:event-out";

	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/syslog-to-event-js.xml");
	}

	@Test
	public void testEventToLog() throws Exception {
		MockEndpoint eventOut = getMockEndpoint(EVENT_OUT);
		eventOut.setExpectedMessageCount(1);
		SyslogMessage sm = new SyslogMessage();
		RawEvent event = new RawEvent();
		
		Map<String,Object> headers = new HashMap<String,Object>();
		
		headers.put("syslog", sm);
		headers.put("event",event);
		
		template.sendBodyAndHeaders(SYSLOG_IN, event,headers);
		
		List<Exchange> exchanges = eventOut.getExchanges();
		
		RawEvent newEvent = exchanges.get(0).getIn().getBody(RawEvent.class);
		
		System.out.println(newEvent);
		
		eventOut.assertIsSatisfied(); 
	}
}