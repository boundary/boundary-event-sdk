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

import java.util.Map;

import org.apache.camel.Header;
import org.apache.camel.Properties;

public class ServiceCheckRouter {
	public String routeServiceTest(
			@Header("serviceTestName") String serviceTestName,
			@Properties Map<String, Object> properties) {
		String endPoint = null;
		int invoked = 0;

		// property will be null on first call
		Object current = properties.get("invoked");
		if (current != null) {
			invoked = Integer.valueOf(current.toString());
		}

		if (invoked == 0) {

			switch (serviceTestName) {
			case "ping":
				endPoint = "seda:ping-check";
				break;
			case "port":
				endPoint = "seda:port-check";
				break;
			default:
				assert false;
			}
		}
		invoked++;
		properties.put("invoked", invoked);

		return endPoint;
	}
}
