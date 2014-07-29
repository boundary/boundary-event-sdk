/**
 * 
 */
package com.boundary.sdk.event.snmp;

import static org.apache.camel.component.exec.ExecBinding.*;
import static com.boundary.sdk.event.snmp.SnmpPollerRouteBuilder.BOUNDARY_HOSTNAME;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.snmp.SnmpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;



/**
 * Implements {@link Processor} interface and is responsible for converting
 * a {@link PDU} to a {@link RawEvent}.
 * 
 * @author davidg
 * 
 */
public class SnmpToExecProcessor implements Processor {
	
	
	private String command = "metric-add";
	
    private static final Logger LOG = LoggerFactory.getLogger(SnmpToExecProcessor.class);

	
	/**
	 * Handles the translation of a {@link snmp} to headers needed to run
	 * Camel exec
	 * 
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		// Extract the SnmpMessage and PDU instances from the Camel Exchange
		Message message = exchange.getIn();
		snmp snmp = message.getBody(snmp.class);
		
		List<String> args = new ArrayList<String>();
		List<entry> entries = snmp.getEntries();
		
		for (entry e : entries) {
			// Add the source
			args.add(message.getHeader(BOUNDARY_HOSTNAME).toString());
			
			// Add the metric name
			args.add("jdg_sample");

			// Add the measure
			args.add(e.getValue());
		}
		
		message.setHeader(EXEC_COMMAND_EXECUTABLE, command);
		message.setHeader(EXEC_COMMAND_ARGS,args);

		message.setBody("");
	}
}
