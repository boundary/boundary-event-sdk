package com.boundary.sdk.event.service.url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.boundary.camel.component.url.UrlConfiguration;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;
import com.boundary.sdk.event.service.db.IServiceModelRecord;

public class ServiceDatabaseUrl implements IServiceModelRecord {

	public void populate(ServiceCheckRequest request, Map<String, Object> row) {
		
		//
		// Extract values from record URL configuration record
		//
		String url = row.get("urlUrl").toString();
		String requestMethod = row.get("urlRequestMethod").toString();
		String requestBody = row.get("urlRequestBody").toString();
		int responseCode = Integer.parseInt(row.get("urlResponseCode").toString());
		String responseBody = row.get("urlResponseBody").toString();
		int responseTime = Integer.parseInt(row.get("urlResponseTime").toString());
		int urlTimeout = Integer.parseInt(row.get("urlRequestTimeout").toString());
		String user = row.get("urlUser") != null ? row.get("urlUser").toString() : null;
		String password = row.get("urlPassword") != null ? row.get("urlPassword").toString() : null;

		//
		// Extract the values from the Service Test record
		String serviceName = row.get("serviceName").toString();
		String serviceTestName = row.get("serviceTestName").toString();
		String serviceTypeName = row.get("serviceTypeName").toString();

		//
		// Populate configuration
		UrlConfiguration urlConfiguration = new UrlConfiguration();
		try {
			urlConfiguration.setUrl(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		urlConfiguration.setRequestMethod(requestMethod);
		urlConfiguration.setUser(user != null ? user : null);
		urlConfiguration.setPassword(password != null ? user : null);

		UrlServiceModel urlServiceModel = new UrlServiceModel();
		urlServiceModel.setResponseBody(responseBody);
		urlServiceModel.setResponseTime(responseTime);
		
		ServiceTest<UrlConfiguration,UrlServiceModel> urlServicetest =
				new ServiceTest<UrlConfiguration,UrlServiceModel>(serviceTestName,serviceTypeName,serviceName,
				request.getRequestId(),urlConfiguration,urlServiceModel);
		request.addServiceTest(urlServicetest);
	}

}
