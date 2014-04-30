package com.boundary.sdk.event;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostConnectionFailure {
	
    private final static Logger LOG = LoggerFactory.getLogger(HostConnectionFailure.class);

	public HostConnectionFailure() {
		
	}
	
    public void handle(Exchange exchange) {
    	Message message = exchange.getIn();
    	RawEvent event = new RawEvent();
    	
    	event.setTitle("Cannot reach host");
    	event.setStatus(Status.OPEN);
    	event.setSeverity(Severity.CRITICAL);
    	event.setMessage("Houston looks like we have a problem");
    	event.getSource().setRef("myhost");
    	event.getSource().setType("host");
    	event.addProperty("host", "myhost");
    	event.addProperty("issue","HOST DOWN");
    	event.addFingerprintField("host");
    	event.addFingerprintField("issue");
    	
    	message.setBody(event);
    }
}
