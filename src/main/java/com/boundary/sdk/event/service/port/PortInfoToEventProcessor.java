/**
 * 
 */
package com.boundary.sdk.event.service.port;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE;

import java.io.IOException;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingInfo;
import com.boundary.camel.component.port.PortInfo;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;
import com.boundary.sdk.event.service.ServiceTest;


/**
 * Responsible for translating a {@link PortInfo} to {@link com.boundary.sdk.event.RawEvent}.
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
		PortInfo info = message.getBody(PortInfo.class);
		
		// Extract {@link ServiceTest} from message headers
		ServiceTest<PortInfo> serviceTest = message.getHeader(SERVICE_TEST_INSTANCE, ServiceTest.class);

		// Create new event to translate the syslog message to
		RawEvent event = new RawEvent();

		// Delegate to member method call to perform the translation
		portInfoToEvent(serviceTest,info,event);
		
		LOG.debug("RawEvent: " + event);

		// Set the message body to the RawEvent
		message.setBody(event);
	}

	/**
	 * Converts a {@link SyslogMessage} to {@link RawEvent}
	 * 
	 * @param sm
	 * @param e
	 */
	private void portInfoToEvent(ServiceTest<PortInfo> serviceTest,PortInfo info, RawEvent event) {

		String hostname = info.getHost();
		String serviceName = serviceTest.getServiceName();
		
		event.getSource().setRef(hostname).setType("host");
		event.addProperty("hostname",hostname);
		event.addProperty("port",info.getPort());
		event.addProperty("port-status",info.getPortStatus());
		event.addProperty("time-out", info.getTimeout());
		event.addProperty("service-test", serviceTest.getName());
		event.addProperty("service-test-type",serviceTest.getServiceTestType());
		event.addProperty("service", serviceName);
		
		event.addTag(serviceName);
		event.addTag(hostname);
		int port = info.getPort();
		
		// Set the Severity and Message based on the results
		// of the service test
		if (info.getStatus() == ServiceStatus.FAIL) {
			event.setTitle(serviceName + " - " + hostname + ":" + port + " is OFFLINE");
			event.setMessage("Failed to connect the port, reason: " + info.getMessage());
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
		
		// Set the time at which the Syslog record was created
		event.setCreatedAt(info.getTimestamp());
		
		// Set Sender
		event.getSender().setRef("Service Health Check");
		event.getSender().setType("Boundary Event SDK");
	}
}
