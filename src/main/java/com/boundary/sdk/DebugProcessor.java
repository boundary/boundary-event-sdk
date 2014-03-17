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

import com.boundary.sdk.Event;


/**
 * @author davidg
 * 
 */
public class DebugProcessor implements Processor {

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
        System.out.println("Received message body: " + exchange.getIn().getBody(String.class));
        Message m = exchange.getIn();
        Object o = m.getBody();
        System.out.println("Class: " + o.getClass());
        Map <String,Object> headers = m.getHeaders();
        System.out.println("headers: " + headers);
	}
}
