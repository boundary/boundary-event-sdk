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
package com.boundary.sdk.event.service.port;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.port.PortResult;
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
public class PortInfoToEventProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(PortInfoToEventProcessor.class);

	public PortInfoToEventProcessor() {
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		portInfoToRawEvent(exchange);
	}
	
	public void portInfoToRawEvent(Exchange exchange) throws Exception {
		
		LOG.debug("PROCESS");
		Message message = exchange.getIn();

		// Extract SyslogMessage from the message body.
		PortResult result = message.getBody(PortResult.class);
		
		// Extract {@link ServiceTest} from message headers
		ServiceTest<PortConfiguration,PortServiceModel> serviceTest = message.getHeader(SERVICE_TEST_INSTANCE, ServiceTest.class);

		// Create new event to translate the syslog message to
		RawEvent event = new RawEvent();

		// Delegate to member method call to perform the translation
		portInfoToEvent(serviceTest,result,event);
		
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
	private void portInfoToEvent(ServiceTest<PortConfiguration,PortServiceModel> serviceTest,PortResult result, RawEvent event) {

		String hostname = result.getHost();
		String serviceName = serviceTest.getServiceName();
		
		event.getSource().setRef(hostname).setType("host");
		event.addProperty("hostname",hostname);
		event.addProperty("port",result.getPort());
		event.addProperty("port-status",result.getPortStatus());
		event.addProperty("time-out", result.getTimeout());
		event.addProperty("service-test", serviceTest.getName());
		event.addProperty("service-test-type",serviceTest.getServiceTestType());
		event.addProperty("service", serviceName);
		
		event.addTag(serviceName);
		event.addTag(hostname);
		int port = result.getPort();
		
		// Set the Severity and Message based on the results
		// of the service test
		if (result.getStatus() == ServiceStatus.FAIL) {
			event.setTitle(serviceName + " - " + hostname + ":" + port + " is OFFLINE");
			event.setMessage("Failed to connect the port, reason: " + result.getMessage());
			event.setSeverity(Severity.WARN);
			event.setStatus(Status.OPEN);
		}
		else {
			event.setTitle(serviceName + " - " + hostname + ":" + port + " is ONLINE");
			event.setMessage("Connected to port " + port + " on " + hostname);
			event.setSeverity(Severity.INFO);
			event.setStatus(Status.CLOSED);
		}
		
		event.addFingerprintField("service");
		event.addFingerprintField("service-test");
		event.addFingerprintField("hostname");
		
		// Set the time at which the port was polled
		event.setCreatedAt(result.getTimestamp());
		
		// Set Sender
		event.getSender().setRef("Service Health Check");
		event.getSender().setType("Boundary Event SDK");
	}
}
