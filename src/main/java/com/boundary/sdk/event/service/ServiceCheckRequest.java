/**
 * 
 */
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
