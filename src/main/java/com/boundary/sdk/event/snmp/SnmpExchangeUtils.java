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

import java.util.Vector;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.snmp.SnmpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.smi.VariableBinding;

public class SnmpExchangeUtils {
	
	private static Logger LOG = LoggerFactory.getLogger(SnmpExchangeUtils.class);
	
	static public SnmpMessage extractSnmpMessage(Exchange exchange) {
		// Extract the SnmpMessage and PDU instances from the Camel Exchange
		Message message = exchange.getIn();
		SnmpMessage snmpMessage = message.getBody(SnmpMessage.class);
		return snmpMessage;
	}
	
	static public PDU extractPDU(Exchange exchange) {
		SnmpMessage snmpMessage = SnmpExchangeUtils.extractSnmpMessage(exchange);
		PDU pdu = snmpMessage.getSnmpMessage();
		return pdu;
	}
	
    static public Vector<? extends VariableBinding> extractVarBinds(Exchange exchange) {
		PDU pdu = SnmpExchangeUtils.extractPDU(exchange);
		Vector<? extends VariableBinding> varBinds = pdu.getVariableBindings();
		LOG.debug("Extracting {} variable bindings from PDU",varBinds.size());
		return varBinds;
    }
    
    static public String extractHost(Exchange exchange) {
		Message message = exchange.getIn();
		String header = message.getHeader("peerAddress",String.class);
		String [] tokens = header.split("/");
		String host = tokens[0];
		return host;
    }
}
