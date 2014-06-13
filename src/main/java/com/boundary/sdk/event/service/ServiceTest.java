package com.boundary.sdk.event.service;

/**
 * Represents an individual test of a service.
 * 
 * @author davidg
 *
 */
public class ServiceTest<T> {
	
	private String name;
	private T configuration;
	private String requestId;
	
	public ServiceTest(String name,String requestId,T configuration) {
		this.name = name;
		this.requestId = requestId;
		this.configuration = configuration;
	}
	
	public String getName() {
		return this.name;
	}
	
	public T getConfiguration() {
		return this.configuration;
	}

	public String getRequestId() {
		return this.requestId;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("[");
		s.append("name=" + getName());
		s.append(",requestId=" + getRequestId());
		s.append(",configuration=" + configuration.toString());
		s.append("]");
		return s.toString();
	}
}
