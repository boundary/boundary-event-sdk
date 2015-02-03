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
package com.boundary.sdk.event.service.ping;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingResult;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.Status;
import com.boundary.sdk.event.service.ServiceTest;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;

/**
 * Responsible for translating a {@link PingResult} to {@link com.boundary.sdk.event.RawEvent}.
 * 
 * @author davidg
 * 
 */
public class PingInfoToEventProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(PingInfoToEventProcessor.class);

	public PingInfoToEventProcessor() {
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		pingInfoToRawEvent(exchange);
	}

	public void pingInfoToRawEvent(Exchange exchange) throws Exception {
		Message message = exchange.getIn();

		// Extract {@link PingInfo} from message body
		PingResult info = message.getBody(PingResult.class);
		
		// Extract {@link ServiceTest} from messsage headers
		ServiceTest<PingResult,PingServiceModel> serviceTest = message.getHeader(SERVICE_TEST_INSTANCE, ServiceTest.class);

		// Create new event to translate the {@link PingInfo}
		RawEvent event = new RawEvent();

		// Delegate to member method call to perform the translation
		pingStatusToEvent(serviceTest,info,event);
		
		LOG.debug("RawEvent: " + event);

		// Set the message body to the RawEvent
		message.setBody(event, RawEvent.class);
	}

	/**
	 * Converts a {@link SyslogMessage} to {@link com.boundary.sdk.event.RawEvent}
	 * 
	 * @param serviceTest {@link ServiceTest}
	 * @param result {@link PingResult}
	 * @param event {@link RawEvent}
	 */
	private void pingStatusToEvent(ServiceTest<PingResult,PingServiceModel> serviceTest,PingResult result,RawEvent event) {
		
		// Add the hostname
		Source s = event.getSource();
		String host = result.getHost();
		assert host != null: "Host is null";
		s.setRef(result.getHost()).setType("host");

		String hostname = result.getHost();
		String serviceName = serviceTest.getServiceName();
		event.addProperty("hostname",hostname);
		event.addProperty("ping-status", result.getStatus());
		event.addProperty("rtt-avg",result.getRTTAvg());
		event.addProperty("rtt-max",result.getRTTMax());
		event.addProperty("rtt-min",result.getRTTMin());
		event.addProperty("service-test", serviceTest.getName());
		event.addProperty("service-test-type",serviceTest.getServiceTestType());
		event.addProperty("service",serviceName);
		
		event.addTag(result.getHost());
		event.addTag(serviceName);

		
		// Set the Severity and Message based on the results
		// of the service test
		if (result.getStatus() == ServiceStatus.FAIL) {
			event.setTitle(serviceName + " - " + hostname + " is DOWN");
			event.setMessage("Ping failed to: " + hostname + ", reason: " + result.getMessage());
			event.setSeverity(Severity.WARN);
			event.setStatus(Status.OPEN);
		}
		else {
			event.setTitle(serviceName + " - " + hostname + " is UP");
			event.setMessage("Ping succeeded to: " + hostname);
			event.setSeverity(Severity.INFO);
			event.setStatus(Status.CLOSED);
		}
		
		event.addFingerprintField("service");
		event.addFingerprintField("service-test");
		event.addFingerprintField("hostname");

		// Set the creation time based on time stamp from the Ping command
		event.setCreatedAt(result.getTimestamp());

		// Set Sender
		event.getSender().setRef("Service Health Check");
		event.getSender().setType("Boundary Event SDK");
	}
}
