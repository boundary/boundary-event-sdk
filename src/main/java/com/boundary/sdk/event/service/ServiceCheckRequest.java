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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a request to check on a service by one or more {@link ServiceTest}s.
 * 
 * @author davidg
 *
 */
public class ServiceCheckRequest {
	
	private String requestId;
	private List<ServiceTest<?,?>> serviceTests;

	/**
	 * Default constructor that generates a {@link UUID}
	 */
	public ServiceCheckRequest() {
		this.requestId = UUID.randomUUID().toString();
		this.serviceTests = new ArrayList<ServiceTest<?,?>>();
	}
	
	/**
	 * Returns the request id associated with this {@link ServiceCheckRequest}
	 * 
	 * @return String containing the unique identifier
	 */
	public String getRequestId() {
		return this.requestId;
	}
	
	public void addServiceTest(ServiceTest<?,?> test) {
		serviceTests.add(test);
	}

	public List<ServiceTest<?,?>> getServiceTests() {
		return this.serviceTests;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("requestId: " + getRequestId());
		//sb.append(",serviceName: " + getServiceName());
		for (ServiceTest<?,?> test : getServiceTests()) {
			sb.append(",serviceTest: " + test);
		}
		return sb.toString();
	}
}
