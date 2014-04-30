package com.boundary.sdk.event.syslog;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;


/**
 * Our first unit test with the Camel Test Kit.
 * We test the Hello World example of integration kits, which is moving a file.
 *
 * @version $Revision$
 */
public class TestSyslogToEvent extends CamelTestSupport {

    public void setUp() throws Exception {
        // delete directories so we have a clean start
        deleteDirectory("target/inbox");
        deleteDirectory("target/outbox");
        super.setUp();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:test").id("TestSyslogToEvent")
                	.log("We got incoming message containing: ${body}")
                    .to("mock:test");
                
            }
        };
    }

    @Test
    public void testDefaultSyslogMessage() throws Exception {
       SyslogMessage message = new SyslogMessage();
    	
        template.sendBody("direct:test", message);

        Thread.sleep(2000);
        
        MockEndpoint endPoint  = getMockEndpoint("mock:test");
        endPoint.setExpectedMessageCount(1);
        		
        //endPoint.expectedBodiesReceived(message);
        
        endPoint.assertIsSatisfied();       
    }

}
