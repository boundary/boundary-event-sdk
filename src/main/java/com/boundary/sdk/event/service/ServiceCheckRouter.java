package com.boundary.sdk.event.service;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
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
