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
package com.boundary.sdk.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyslogEventAdapter extends CamelApplication {
	
	private static Logger LOG = LoggerFactory.getLogger(SyslogEventAdapter.class);

	
	private static final String SYSLOG_EVENT_ADAPTER_URI =
			"META-INF/spring/syslog-event-adapter.xml";
	private static final String SYSLOG_EVENT_ADAPTER_NAME = "Syslog Event Adapter";
	
	public SyslogEventAdapter() {
		super(SYSLOG_EVENT_ADAPTER_URI,SYSLOG_EVENT_ADAPTER_NAME);
	}
	
	public static void main(String [] args) {
		SyslogEventAdapter sea = new SyslogEventAdapter();
		try {
			sea.boot();
		} catch (Exception e) {
			LOG.error("{}",e.getMessage());
		}
	}
}
