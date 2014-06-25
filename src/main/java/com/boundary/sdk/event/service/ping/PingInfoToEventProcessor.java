/**
 * 
 */
package com.boundary.sdk.event.service.ping;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingInfo;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.Status;
import com.boundary.sdk.event.service.ServiceTest;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;

/**
 * Responsible for translating a {@link PingInfo} to {@link com.boundary.sdk.event.RawEvent}.
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
		PingInfo info = message.getBody(PingInfo.class);
		
		// Extract {@link ServiceTest} from messsage headers
		ServiceTest<PingInfo> serviceTest = message.getHeader(SERVICE_TEST_INSTANCE, ServiceTest.class);

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
	 * @param info {@link PingInfo}
	 * @param e {@link RawEvent}
	 */
	private void pingStatusToEvent(ServiceTest<PingInfo> serviceTest,PingInfo info,RawEvent event) {
		
		// Add the hostname
		Source s = event.getSource();
		String host = info.getHost();
		assert host != null: "Host is null";
		s.setRef(info.getHost()).setType("host");

		String hostname = info.getHost();
		String serviceName = serviceTest.getServiceName();
		event.addProperty("hostname",hostname);
		event.addProperty("ping-status", info.getStatus());
		event.addProperty("rtt-avg",info.getRTTAvg());
		event.addProperty("rtt-max",info.getRTTMax());
		event.addProperty("rtt-min",info.getRTTMin());
		event.addProperty("rtt-mdev",info.getRTTMDev());
		event.addProperty("service-test", serviceTest.getName());
		event.addProperty("service-test-type",serviceTest.getServiceTestType());
		event.addProperty("service",serviceName);
		
		event.addTag(info.getHost());
		event.addTag(serviceName);

		
		// Set the Severity and Message based on the results
		// of the service test
		if (info.getStatus() == ServiceStatus.FAIL) {
			event.setTitle(serviceName + " - " + hostname + " is DOWN");
			event.setMessage("Ping failed to: " + hostname + ", reason: " + info.getMessage());
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
		event.setCreatedAt(info.getTimestamp());

		// Set Sender
		event.getSender().setRef("Service Health Check");
		event.getSender().setType("Boundary Event SDK");
	}
}
