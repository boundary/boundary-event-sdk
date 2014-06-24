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
	 * @param test {@link ServiceTest}
	 * @param info {@link PingInfo}
	 * @param e {@link RawEvent}
	 */
	private void pingStatusToEvent(ServiceTest<PingInfo> test,PingInfo info,RawEvent e) {
		
		// Add the hostname
		Source s = e.getSource();
		String host = info.getHost();
		assert host != null: "Host is null";
		s.setRef(info.getHost()).setType("host");

		HashMap<String,Object> p = e.getProperties();
		p.put("hostname", info.getHost());
		p.put("ping-status", info.getStatus());
		p.put("rtt-avg",info.getRTTAvg());
		p.put("rtt-max",info.getRTTMax());
		p.put("rtt-min",info.getRTTMin());
		p.put("rtt-mdev",info.getRTTMDev());
		p.put("service-test", test.getName());
		p.put("service", test.getServiceName());
		e.addTag(info.getHost());
		
		// Add the message
		e.setMessage(info.getMessage());
		
		// Map the syslog severity to Boundary event severity
		Severity severity = info.getStatus() == ServiceStatus.FAIL ? Severity.CRITICAL : Severity.INFO;
		e.setSeverity(severity);	

		if (e.getSeverity() != Severity.INFO) {
			e.setStatus(Status.OPEN);
		}
		else {
			e.setStatus(Status.CLOSED);
		}
		
		// Set the uniqueness of the event by hostname, facility, and message.
		// TBD: These fields need to be split out in a configuration file
		e.addFingerprintField("hostname");
		e.addFingerprintField("@message");
		
		// Set the time at which the Syslog record was created
		e.setCreatedAt(info.getTimestamp());

		// Set Title
		e.setTitle("Ping status for " + info.getHost());
		
		// Set Sender
		e.getSender().setRef("Ping");
		e.getSender().setType("Boundary Event SDK");
	}
}
