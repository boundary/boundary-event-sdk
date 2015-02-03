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

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.syslog.SyslogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
