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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.syslog.SyslogFacility;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.boundary.sdk.event.RawEvent;

/**
 * Test {@link SyslogToEvent} processor
 *
 */
public class SyslogToEventProcessorTest extends CamelTestSupport {
	
	private final static SyslogFacility EXPECTED_SYSLOG_FACILITY = SyslogFacility.DAEMON;
	private final static String EXPECTED_HOST="foobar";
	private final static String EXPECTED_SOURCE_TYPE="host";
	private final static String EXPECTED_LOCAL_ADDRESS="127.0.0.1";
	private final static String EXPECTED_LOG_MESSAGE="Hello Word";
	private final static String EXPECTED_REMOTE_ADDRESS="10.10.10.10";
	private final static SyslogSeverity EXPECTED_SYSLOG_SEVERITY=SyslogSeverity.ALERT;
	
    @Produce(uri = "direct:in-translate")
    protected ProducerTemplate inTranslate;
    @EndpointInject(uri = "mock:out")
    protected MockEndpoint out;
    
    @Produce(uri = "direct:in-no-translate")
    protected ProducerTemplate inNoTranslate;
    
	private SyslogMessage syslogMessage;
	private RawEvent event;
	private List<String> expectedTags;
	private Map<String,Object> expectedProperties;
	private List<String> expectedFingerprintFields;
    
	@Before
	public void setUp() throws Exception {
		Date now = new Date();
		super.setUp();
    	Builder builder = new Calendar.Builder();
    	builder.setInstant(now);

		syslogMessage = buildSyslogMessage(
				EXPECTED_SYSLOG_FACILITY,
    			EXPECTED_HOST,
    			EXPECTED_LOCAL_ADDRESS,
    			EXPECTED_LOG_MESSAGE,
    			EXPECTED_REMOTE_ADDRESS,
    			EXPECTED_SYSLOG_SEVERITY,
    			builder.build());
		
	   	event = new RawEvent();


	   	event.addTag(SyslogFacility.DAEMON.toString());
	   	event.getSource().setRef(EXPECTED_HOST).setType(EXPECTED_SOURCE_TYPE);
	   	event.addTag(EXPECTED_SYSLOG_FACILITY.toString());
	   	event.addTag(EXPECTED_HOST);
	   	event.setCreatedAt(now);
	   	event.addFingerprintField("facility");
	   	event.addFingerprintField("hostname");
	   	event.addFingerprintField("message");
	   	event.setTitle(String.format("Syslog message from: %s",EXPECTED_HOST));
	   	event.setMessage(EXPECTED_LOG_MESSAGE);
	   	
	   	expectedTags = new ArrayList<String>();
	   	expectedTags.add(SyslogFacility.DAEMON.toString());
	   	expectedTags.add(EXPECTED_HOST);
	   	 
	   	expectedProperties = new HashMap<String,Object>();
	   	expectedProperties.put("facility",SyslogFacility.DAEMON.toString());
	   	expectedProperties.put("hostname",EXPECTED_HOST);
	   	expectedProperties.put("message",EXPECTED_LOG_MESSAGE);

	   	expectedFingerprintFields = new ArrayList<String>();
	   	expectedFingerprintFields.add("facility");
	   	expectedFingerprintFields.add("hostname");
	   	expectedFingerprintFields.add("message");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		this.syslogMessage = null;
		this.expectedTags = null;
	}
 
    private SyslogMessage buildSyslogMessage(
    		SyslogFacility facility,
    		String hostname,
    		String localAddress,
    		String logMessage,
    		String remoteAddress,
    		SyslogSeverity severity,
    		Calendar timestamp) {
    	
    	SyslogMessage message = new SyslogMessage();
    	message.setFacility(facility);
    	message.setHostname(hostname);
    	message.setLocalAddress(localAddress);
    	message.setLogMessage(logMessage);
    	message.setRemoteAddress(remoteAddress);
    	message.setSeverity(severity);
    	message.setTimestamp(timestamp);
    	return message;
    }
 
    @Test
    public void testTranslateEvent() throws Exception {
    	
        out.expectedMessageCount(1);
 
    	inTranslate.sendBody(syslogMessage);
 
        out.assertIsSatisfied();
        
        
        List<Exchange> exchanges = out.getExchanges();
        Exchange exchange = exchanges.get(0);
        RawEvent newEvent = exchange.getIn().getBody(RawEvent.class);
        
        assertEquals("check Tags",this.expectedTags,newEvent.getTags());
        assertEquals("check Source name",EXPECTED_HOST,newEvent.getSource().getRef());
        assertEquals("check Source type",EXPECTED_SOURCE_TYPE,newEvent.getSource().getType());
        assertTrue("check fingerprint fields #1",newEvent.getFingerprintFields().containsAll(expectedFingerprintFields));
        assertTrue("check fingerprint fields #2",expectedFingerprintFields.containsAll(newEvent.getFingerprintFields()));
        //@TODO
        //assertTrue("check properties",expectedProperties.equals(newEvent.getProperties()));
    }
    
    @Test
    public void testEventEquals() throws InterruptedException {
        out.expectedMessageCount(1);
        
    	inTranslate.sendBody(syslogMessage);
 
        out.assertIsSatisfied();
        
        List<Exchange> exchanges = out.getExchanges();
        Exchange exchange = exchanges.get(0);
        RawEvent newEvent = exchange.getIn().getBody(RawEvent.class);
        assertNotNull(newEvent);
        assertEquals("check exchange count",1,exchanges.size());
    }
    
    @Ignore
    @Test
    public void testTranslate() throws InterruptedException {
        out.expectedMessageCount(1);
        
    	inNoTranslate.sendBody(syslogMessage);
        out.assertIsSatisfied();
        
        List<Exchange> exchanges = out.getExchanges();
        assertEquals("check exchange count",1,exchanges.size());
        Exchange exchange = exchanges.get(0);
        assertNotNull("check for Null Exchange",exchange);
        RawEvent newEvent = exchange.getIn().getBody(RawEvent.class);

        assertTrue("no translate check",event.equals(newEvent));
    }
    
    @Test
    public void testNoTranslate() throws InterruptedException {
    	
        out.expectedMessageCount(1);
    	inNoTranslate.sendBody(syslogMessage);
        out.assertIsSatisfied();
        
        List<Exchange> exchanges = out.getExchanges();
        assertEquals("check exchange count",1,exchanges.size());
        Exchange exchange = exchanges.get(0);
        assertNotNull("check for Null Exchange",exchange);
        SyslogMessageEvent event = exchange.getIn().getBody(SyslogMessageEvent.class);

        assertEquals("check host name",syslogMessage.getHostname(),event.getHostname());
        assertEquals("check facility",syslogMessage.getFacility().toString(),event.getFacility());
        assertEquals("check local address",syslogMessage.getLocalAddress(),event.getLocalAddress());
        assertEquals("check log message",syslogMessage.getLogMessage(),event.getLogMessage());
        assertEquals("check remote address",syslogMessage.getRemoteAddress(),event.getRemoteAddress());
        assertEquals("check severity",syslogMessage.getSeverity().toString(),event.getSeverity());
        assertEquals("check timestamp",syslogMessage.getTimestamp().getTime(),event.getTimestamp());
    }
 
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:in-translate")
                .process(new SyslogToEventProcessor(true))
                .to("mock:out");
                
                from("direct:in-no-translate")
                .process(new SyslogToEventProcessor(false))
                .to("mock:out");
            }
        };
    }
    

}
