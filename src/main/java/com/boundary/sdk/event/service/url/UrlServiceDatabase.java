package com.boundary.sdk.event.service.url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.boundary.camel.component.url.UrlConfiguration;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;
import com.boundary.sdk.event.service.db.IServiceModelRecord;

public class UrlServiceDatabase implements IServiceModelRecord {

	public void populate(ServiceCheckRequest request, Map<String, Object> row) {
		
		//
		// Extract values from record URL configuration record
		//
		String url = row.get("urlUrl").toString();
		String requestMethod = row.get("urlRequestMethod").toString();
		String requestBody = row.get("urlRequestBody").toString();
		int responseCode = Integer.parseInt(row.get("urlResponseCode").toString());
		String responseBody = row.get("urlResponseBody").toString();
		boolean responseIgnoreBody = Boolean.valueOf(row.get("urlResponseIgnoreBody").toString());
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
		UrlConfiguration configuration = new UrlConfiguration();
		try {
			configuration.setUrl(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		configuration.setRequestMethod(requestMethod);
		// null values indicate that the value was not specified
		configuration.setUser(user != null ? user : null);
		configuration.setPassword(password != null ? user : null);

		UrlServiceModel serviceModel = new UrlServiceModel();
		serviceModel.setResponseBody(responseBody);
		serviceModel.setResponseIgnoreBody(responseIgnoreBody);
		serviceModel.setResponseTime(responseTime);
		serviceModel.setResponseCode(responseCode);
		
		ServiceTest<UrlConfiguration,UrlServiceModel> urlServicetest =
				new ServiceTest<UrlConfiguration,UrlServiceModel>(serviceTestName,serviceTypeName,serviceName,
				request.getRequestId(),configuration,serviceModel);
		request.addServiceTest(urlServicetest);
	}
}
