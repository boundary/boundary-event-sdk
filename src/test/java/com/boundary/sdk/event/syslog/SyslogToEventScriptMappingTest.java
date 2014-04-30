package com.boundary.sdk.event.syslog;

import java.util.HashMap;
import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.context.support.AbstractXmlApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.camel.component.syslog.SyslogMessage;
//import org.apache.camel.component.syslog.SyslogFacility;
//import org.apache.camel.component.syslog.SyslogSeverity;
//import org.apache.camel.component.syslog.SyslogConstants;
import com.boundary.sdk.event.BaseScriptTest;

//import com.boundary.sdk.SyslogToEventScriptingTest;
import com.boundary.sdk.event.EventMapperProcessor;
import com.boundary.sdk.event.RawEvent;

/**
 * Smoke test to test mapping of a {@link SyslogMessage} to a {@link RawEvent}
 * 
 * @author davidg
 *
 */

public class SyslogToEventScriptMappingTest extends BaseScriptTest {
	
	SyslogMessage sm;
	RawEvent e;
	HashMap<String,Object> headers;

	Endpoint in;
	MockEndpoint out;
	
	private static Logger LOG = LoggerFactory.getLogger(SyslogToEventScriptMappingTest.class);
	
	private static final String SYSLOG_JS_IN = "direct:syslog-js-in";
	private static final String EVENT_JS_OUT = "mock:event-js-out";
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		LOG.debug("setup");
		
		sm = new SyslogMessage();
		e = new RawEvent();
		headers = new HashMap<String,Object>();
		headers.put("syslog", sm);
		headers.put("event",e);
	}

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
             @Override
            public void configure() throws Exception {
            	EventMapperProcessor eventMapper = new EventMapperProcessor();
                
                eventMapper.setScriptName("myscript.js");

                from(SYSLOG_JS_IN)
                    .process(eventMapper)
                    .to(EVENT_JS_OUT);
            }
        };
    }

	/**
	 * Utility
	 * @param in
	 * @param out
	 * @throws InterruptedException
	 */
	private RawEvent getEvent(String inUrl, String outUrl) {
//		in = getMandatoryEndpoint(inUrl);
//		assertNotNull(in);
//		out = getMockEndpoint(outUrl);
//		assertNotNull(out);


		template.sendBodyAndHeaders(in,e,headers);

		List<Exchange> exchanges = out.getExchanges();
		RawEvent newEvent = exchanges.get(0).getIn().getBody(RawEvent.class);
		
		assertNotNull(newEvent);
		

		return newEvent;
	}
	
	/**
	 * 
	 */
	@Ignore("Not implemented")
	@Test
	public void smokeTest() {
		in = getMandatoryEndpoint(SYSLOG_JS_IN);
		assertNotNull(in);
		out = getMockEndpoint(EVENT_JS_OUT);
		assertNotNull(out);
	}
	
	/**
	 * 
	 * Java Script
	 * 
	 * @throws Exception
	 */
	@Ignore("Not implemented")
	@Test
	public void testSysLogToEventJavaScript() throws InterruptedException {
		out.setExpectedMessageCount(1);
		
		getEvent(SYSLOG_JS_IN,EVENT_JS_OUT);

		out.assertIsSatisfied();
	}
	
}