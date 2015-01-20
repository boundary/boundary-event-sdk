// Copyright 2014 Boundary, Inc.
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
package com.boundary.sdk.event.service;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.boundary.sdk.event.RawEvent;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;

public class ServiceCheckToRawEvent {

	/**
	 * 
	 * @param exchange {@link Exchange} from a Camel route
	 */
	public void toEvent(Exchange exchange) {
		Message message = exchange.getIn();
		ServiceCheckRequest request = message.getHeader(SERVICE_CHECK_REQUEST_INSTANCE,ServiceCheckRequest.class);
		ServiceCheckResults results = message.getBody(ServiceCheckResults.class);
		
		RawEvent event = new RawEvent();
		
		message.setBody(event);
	}
}

