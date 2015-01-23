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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostConnectionFailure {
	
    private final static Logger LOG = LoggerFactory.getLogger(HostConnectionFailure.class);

	public HostConnectionFailure() {
		
	}
	
    public void handle(Exchange exchange) {
    	Message message = exchange.getIn();
    	RawEvent event = new RawEvent();
    	
    	event.setTitle("Cannot reach host");
    	event.setStatus(Status.OPEN);
    	event.setSeverity(Severity.CRITICAL);
    	event.setMessage("Houston looks like we have a problem");
    	event.getSource().setRef("myhost");
    	event.getSource().setType("host");
    	event.addProperty("host", "myhost");
    	event.addProperty("issue","HOST DOWN");
    	event.addFingerprintField("host");
    	event.addFingerprintField("issue");
    	
    	message.setBody(event);
    }
}
