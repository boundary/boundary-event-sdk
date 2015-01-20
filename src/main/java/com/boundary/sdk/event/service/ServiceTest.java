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

/**
 * Represents an individual test of a service.
 * 
 *
 */
public class ServiceTest<C,M> {
	
	private String name;
	private String serviceName;
	private C configuration;
	private M model;
	private String requestId;
	private String serviceTestType;
	
	public ServiceTest(
			String name,
			String serviceTestType,
			String serviceName,
			String requestId,
			C configuration,
			M model) {
		this.name = name;
		this.serviceTestType = serviceTestType;
		this.serviceName = serviceName;
		this.requestId = requestId;
		this.configuration = configuration;
		this.model = model;
	}
	
	public String getName() {
		return this.name;
	}
	
	public C getConfiguration() {
		return this.configuration;
	}
	
	public M getModel() {
		return this.model;
	}

	public String getRequestId() {
		return this.requestId;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("[");
		s.append("name=" + getName());
		s.append(",serviceType=" + getServiceTestType());
		s.append(",requestId=" + getRequestId());
		s.append(",configuration=" + configuration.toString());
		s.append("]");
		return s.toString();
	}

	public String getServiceName() {
		return this.serviceName;
	}
	
	public String getServiceTestType() {
		return serviceTestType;
	}
}
