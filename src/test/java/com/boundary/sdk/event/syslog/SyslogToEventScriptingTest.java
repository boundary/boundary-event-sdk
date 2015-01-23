// Copyright 2014-2015 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.sdk.event.syslog;

import java.util.HashMap;
import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.RawEvent;

/**
 * Smoke test to test mapping of a {@link SyslogMessage} to a {@link RawEvent}
 *
 */
public class SyslogToEventScriptingTest extends CamelSpringTestSupport {
	
	SyslogMessage sm;
	RawEvent e;
	HashMap<String,Object> headers;
	
	private static Logger LOG = LoggerFactory.getLogger(SyslogToEventScriptingTest.class);
	
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
	
//	@Ignore("Requires access to Boundary Server")
//    @Override
//    protected RouteBuilder[] createRouteBuilders() throws Exception {
//    	RouteBuilder[] routes = new RouteBuilder[1];
//		
//		routes[0] = new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//            	
//            	from(SYSLOG_JS_IN)
//            	.routeId("foo")
//            	.to("language:javascript:classpath:syslog_to_event.js")
//            	.to(EVENT_JS_OUT)
//            	;
//            }
//        };
//        
//        return routes;
//    }
	
	/**
	 * Utility
	 * @param in
	 * @param out
	 * @throws InterruptedException
	 */
	private RawEvent getEvent(String inUrl, String outUrl) {
		Endpoint in = getMandatoryEndpoint(inUrl);
		assertNotNull(in);
		MockEndpoint out = getMockEndpoint(outUrl);
		assertNotNull(out);

		out.setExpectedMessageCount(1);

		template.sendBodyAndHeaders(in,e,headers);

		List<Exchange> exchanges = out.getExchanges();
		RawEvent newEvent = exchanges.get(0).getIn().getBody(RawEvent.class);
		
		assertNotNull(newEvent);
		
		try {
			out.assertIsSatisfied();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			LOG.debug("MockEndpoint interrupted");
		}

		return newEvent;
	}
	
	/**
	 * 
	 * Java Script
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSysLogToEventJavaScript(){
		getEvent(SYSLOG_JS_IN,EVENT_JS_OUT);
	}
	
	/**
	 * Python
	 * 
	 * @throws InterruptedException
	 */
	@Ignore("Scripting using python not implemented yet.")
	@Test
	public void testSysLogToEventPython() {
		getEvent(SYSLOG_PY_IN,EVENT_PY_OUT);
	}
	
	/**
	 * Ruby
	 * 
	 * @throws InterruptedException
	 */
	@Ignore("Scripting using ruby not implemented yet.")
	@Test
	public void testSysLogToEventRuby() {
		getEvent(SYSLOG_RB_IN,EVENT_RB_OUT);
	}
	
	/**
	 * Set the properties on an event
	 */
	//@Test
	void testSettingEventProperties() {
		
	}
}