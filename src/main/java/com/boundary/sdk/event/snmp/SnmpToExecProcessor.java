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
package com.boundary.sdk.event.snmp;

import static org.apache.camel.component.exec.ExecBinding.*;
import static com.boundary.sdk.event.snmp.SnmpPollerRouteBuilder.BOUNDARY_HOSTNAME;
import static com.boundary.sdk.event.util.PropertyUtils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.snmp.SnmpMessage;
import org.apache.camel.component.syslog.SyslogSeverity;
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
		entry entry = message.getBody(entry.class);
		
		List<String> args = new ArrayList<String>();

		// Add the source
		args.add(message.getHeader(BOUNDARY_HOSTNAME).toString());
			
		// Add the metric name
		String metricName = getMetricName(entry.getOid());
		args.add(metricName);

		// Add the measure
		args.add(entry.getValue());
		
		message.setHeader(EXEC_COMMAND_EXECUTABLE, command);
		message.setHeader(EXEC_COMMAND_ARGS,args);

		message.setBody("");
	}
	
	private static Properties metricNameMap = new Properties();
	
	protected static String getMetricName(String severity) {
		getProperties(metricNameMap,"snmp.metric.names.properties");
		return metricNameMap.getProperty(severity.toString(),"UNKNOWN");
	}

}
