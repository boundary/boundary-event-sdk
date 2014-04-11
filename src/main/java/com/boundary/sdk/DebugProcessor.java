/**
 * 
 */
package com.boundary.sdk;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.syslog.SyslogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.RawEvent;


/**
 * @author davidg
 * 
 */
public class DebugProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(DebugProcessor.class);

	private boolean debug = false;
	/**
	 * 
	 */
	
	public DebugProcessor() {
		this(false);
	}
	public DebugProcessor(boolean debug) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
        LOG.debug("Received message body: " + exchange.getIn().getBody(String.class));
        Message m = exchange.getIn();
        Object o = m.getBody();
        LOG.debug("Class: " + o.getClass());
        Map <String,Object> headers = m.getHeaders();
        LOG.debug("headers: " + headers);
	}
}
