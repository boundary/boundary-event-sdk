package com.boundary.sdk.event.service;

import java.util.List;

import org.apache.camel.Exchange;

public class ServiceCheckRouter {
	public String getEndPointsFromRequest(ServiceCheckRequest request) {
		
		StringBuffer endPoints = new StringBuffer();
		List<ServiceTest<?>> tests = request.getServiceTests();
		boolean first = true;
		for (ServiceTest<?> test :tests) {
			if (first == false) {
				endPoints.append(",");
			}
			endPoints.append(test.getName());
			first = false;
		}
		
		return endPoints.toString();
	}
}
