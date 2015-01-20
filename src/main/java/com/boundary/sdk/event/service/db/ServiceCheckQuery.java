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
package com.boundary.sdk.event.service.db;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceCheckQuery implements Processor {
	
	private static Logger LOG = LoggerFactory.getLogger(ServiceCheckQuery.class);

	public ServiceCheckQuery() {
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		Map<String,Object> map = message.getBody(Map.class);

		//TODO: Place to put these constants like serviceCheckId? Standard format, e.g. all lower case??
		int serviceCheckId = Integer.parseInt(map.get("serviceCheckId").toString());
		
		String sql = "SELECT * FROM v_service_check_configs WHERE serviceCheckId = " + serviceCheckId;
		LOG.warn("Service Test Query: " + sql);
		message.setBody(sql);
	}
}
