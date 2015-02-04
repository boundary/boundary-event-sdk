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
package com.boundary.sdk.event.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.CamelApplication;
import com.boundary.sdk.event.SNMPTrapEventAdapter;

public class SNMPCollector extends CamelApplication {
	
	private static Logger LOG = LoggerFactory.getLogger(SNMPTrapEventAdapter.class);

	private static final String SNMP_POLLER_URI =
			"META-INF/spring/snmp-collector.xml";
	private static final String SNMP_POLLER_NAME = "SNMP Collector";
	
	public SNMPCollector() {
		super(SNMP_POLLER_URI,SNMP_POLLER_NAME);
	}
	
	public static void main(String [] args) {
		SNMPCollector application = new SNMPCollector();
		try {
			application.boot();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("{}",e.getMessage());
		}
	}

}
