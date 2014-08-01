// Copyright 2014 Boundary, Inc.
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
package com.boundary.sdk.event.service.url;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingResult;
import com.boundary.camel.component.url.UrlConfiguration;
import com.boundary.camel.component.port.PortResult;
import com.boundary.camel.component.url.UrlResult;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;
import com.boundary.sdk.event.service.ServiceTest;



/**
 * Responsible for translating a {@link PortResult} to {@link com.boundary.sdk.event.RawEvent}.
 * 
 * @author davidg
 * 
 */
public class UrlResultToEventProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(UrlResultToEventProcessor.class);

	public UrlResultToEventProcessor() {
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		UrlResultToRawEvent(exchange);
	}
	
	public void UrlResultToRawEvent(Exchange exchange) throws Exception {
		Message message = exchange.getIn();

		// Extract UrlResult from the message body.
		UrlResult result = message.getBody(UrlResult.class);
		
		// Extract {@link ServiceTest} from message headers
		ServiceTest<UrlConfiguration,UrlServiceModel> serviceTest = message.getHeader(SERVICE_TEST_INSTANCE, ServiceTest.class);

		// Create new event to translate the syslog message to
		RawEvent event = new RawEvent();

		// Delegate to member method call to perform the translation
		urlResultToEvent(serviceTest,result,event);
		
		LOG.debug("RawEvent: " + event);

		// Set the message body to the RawEvent
		message.setBody(event);
	}

	/**
	 * Converts a {@link SyslogMessage} to {@link RawEvent}
	 * 
	 * @param serviceTest {@link ServiceTest}
	 * @param result {@link PortResult}
	 * @param event {@link RawEvent}
	 */
	private void urlResultToEvent(ServiceTest<UrlConfiguration,UrlServiceModel> serviceTest,UrlResult result, RawEvent event) {

		String hostname = result.getHost();
		String serviceName = serviceTest.getServiceName();
		String serviceTestName = serviceTest.getName();
		
		event.getSource().setRef(hostname).setType("host");
		event.addProperty("hostname",hostname);
		event.addProperty("time-out", result.getTimeout());
		event.addProperty("service-test", serviceTestName);
		event.addProperty("service-test-type",serviceTest.getServiceTestType());
		event.addProperty("service", serviceName);
		event.addProperty("elapsed-time", result.getElapsedTime());
		
		event.addTag(serviceName);
		event.addTag(hostname);
		
		// Set the Severity and Message based on the results
		// of the service test
		if (result.getStatus() == ServiceStatus.FAIL) {
			event.setTitle(serviceName + " - " + serviceTestName + " - FAIL");
			event.setMessage("Failed to connect to: " + result.getURL());
			event.setSeverity(Severity.WARN);
			event.setStatus(Status.OPEN);
		}
		else {
			event.setTitle(serviceName + " - " + serviceTestName + " - SUCCEED");
			event.setMessage("Connected to: " + result.getURL());
			event.setSeverity(Severity.INFO);
			event.setStatus(Status.CLOSED);
		}
		
		event.addFingerprintField("service");
		event.addFingerprintField("service-test");
		event.addFingerprintField("hostname");
		
		// Set Sender
		event.getSender().setRef("Service Health Check");
		event.getSender().setType("Boundary Event SDK");
	}
}
