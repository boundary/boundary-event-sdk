package com.boundary.sdk;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Smoke test to test mapping of a {@link SyslogMessage} to a {@link RawEvent}
 * 
 * @author davidg
 *
 */
@SuppressWarnings("deprecation")
public class SyslogToEventScriptingTest extends CamelSpringTestSupport {
	
	SyslogMessage sm;
	RawEvent e;
	HashMap<String,Object> headers;
	
	//private static Logger LOG = LoggerFactory.getLogger(SyslogToEventScriptingTest.class);
	
	private static final String SYSLOG_JS_IN = "direct:syslog-js-in";
	private static final String EVENT_JS_OUT = "mock:event-js-out";
	
	private static final String SYSLOG_PY_IN = "direct:syslog-py-in";
	private static final String EVENT_PY_OUT = "mock:event-py-out";

	private static final String SYSLOG_RB_IN = "direct:syslog-rb-in";
	private static final String EVENT_RB_OUT = "mock:event-rb-out";

	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/syslog-to-event-scripting.xml");
	}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		sm = new SyslogMessage();
		e = new RawEvent();
		headers = new HashMap<String,Object>();
		headers.put("syslog", sm);
		headers.put("event",e);
	}
	/**
	 * Utility
	 * @param in
	 * @param out
	 * @throws InterruptedException
	 */
	private void getEvent(String inUrl, String outUrl) throws InterruptedException {
		Endpoint in = getMandatoryEndpoint(inUrl);
		assertNotNull(in);
		MockEndpoint out = getMockEndpoint(outUrl);
		assertNotNull(out);

		out.setExpectedMessageCount(1);

		template.sendBodyAndHeaders(in,e,headers);

		List<Exchange> exchanges = out.getExchanges();
		RawEvent newEvent = exchanges.get(0).getIn().getBody(RawEvent.class);
		
		System.out.println(newEvent);
		
		out.assertIsSatisfied(); 
	}
	
	/**
	 * 
	 * Java Script
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSysLogToEventJavaScript() throws Exception {
		getEvent(SYSLOG_JS_IN,EVENT_JS_OUT);
	}
	
	/**
	 * Python
	 * 
	 * @throws InterruptedException
	 */
	//@Test
	public void testSysLogToEventPython() throws InterruptedException {
		getEvent(SYSLOG_PY_IN,EVENT_PY_OUT);
	}
	
	/**
	 * Ruby
	 * 
	 * @throws InterruptedException
	 */
	//@Test
	public void testSysLogToEventRuby() throws InterruptedException {
		getEvent(SYSLOG_RB_IN,EVENT_RB_OUT);
	}
}