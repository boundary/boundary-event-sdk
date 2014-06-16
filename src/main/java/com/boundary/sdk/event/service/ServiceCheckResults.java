package com.boundary.sdk.event.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceCheckResults {
	
	private List<ServiceTest<?>> list = new ArrayList<ServiceTest<?>>();

	public ServiceCheckResults() {

	}

	public void add(ServiceTest test) {
		list.add(test);
	}
	
	public List<ServiceTest<?>> getServiceTests() {
		return this.list;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		for(ServiceTest<?> test :list) {
			s.append(test.getName());
		}
		return s.toString();
	}
}
