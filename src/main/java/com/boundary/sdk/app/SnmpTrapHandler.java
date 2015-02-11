//Copyright 2014-2015 Boundary, Inc.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package com.boundary.sdk.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.CamelApplication;
import com.boundary.sdk.event.SNMPTrapEventAdapter;

public class SnmpTrapHandler extends CamelApplication {
	
	private static Logger LOG = LoggerFactory.getLogger(SnmpTrapHandler.class);

	private static final String SNMP_TRAP_HANDLER_URI =
			"META-INF/spring/snmp-trap-handler.xml";
	private static final String SNMP_TRAP_HANDLER_NAME = "SNMP Trap Handler";
	
	public SnmpTrapHandler() {
		super(SNMP_TRAP_HANDLER_URI,SNMP_TRAP_HANDLER_NAME);
	}
	
	public static void main(String [] args) {
		SnmpTrapHandler application = new SnmpTrapHandler();
		try {
			application.boot();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("{}",e.getMessage());
		}
	}

}
