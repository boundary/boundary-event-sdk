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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.VariableBinding;

/**
 * Handles the exceptions from SNMP polling
 */
public class SnmpHandleException implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(SnmpHandleException.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		
		VariableBinding varBind = message.getBody(VariableBinding.class);

		if (varBind.isException()) {
			LOG.warn("VariableBind Exception: {},",varBind);
			exchange.setProperty(Exchange.ROUTE_STOP, Boolean.TRUE); 
		}
	}
}
