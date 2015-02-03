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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;


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
        endPoint.assertIsSatisfied();       
    }

}
