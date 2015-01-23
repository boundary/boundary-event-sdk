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

import org.junit.Test;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;
import org.apache.camel.component.syslog.SyslogFacility;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;

/**
 * @version $Revision$
 */
public class SyslogMessageConverterTest extends CamelTestSupport {
	
	public final String DEFAULT_HOSTNAME = "localhost";
	public final SyslogFacility DEFAULT_FACILITY = SyslogFacility.LOCAL0;
	public final SyslogSeverity DEFAULT_SEVERITY = SyslogSeverity.INFO;
	
	SyslogMessage message;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		message = new SyslogMessage();
        message.setHostname(DEFAULT_HOSTNAME);
        message.setFacility(DEFAULT_FACILITY);
        message.setSeverity(DEFAULT_SEVERITY);
		
	}

	@Test
    public void sysLogMessageConverter() throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start").convertBodyTo(RawEvent.class);
            }
        });
        context.start();

        ProducerTemplate template = context.createProducerTemplate();

        SyslogMessage message = new SyslogMessage();

        RawEvent event = template.requestBody("direct:start", message, RawEvent.class);
        assertNotNull(event);

        System.out.println(event);
        
        assertNotNull(event.getSeverity());

        assertEquals(Severity.WARN, event.getSeverity());
    }

}
