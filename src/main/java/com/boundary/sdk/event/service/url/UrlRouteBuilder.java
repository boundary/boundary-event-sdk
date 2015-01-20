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
package com.boundary.sdk.event.service.url;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.ping.PingResult;
import com.boundary.camel.component.url.UrlConfiguration;
import com.boundary.sdk.event.BoundaryRouteBuilder;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;

/**
 *
 */
public class UrlRouteBuilder extends BoundaryRouteBuilder {
	
	private String host;
	private int port;
	private int delay = 5;
	
	public UrlRouteBuilder() {
		host = "localhost";
		port = 7;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public int getDelay() {
		return this.delay;
	}

	@Override
	public void configure() throws Exception {
		ServiceCheckRequest request = new ServiceCheckRequest();
		UrlConfiguration config = new UrlConfiguration();
		UrlServiceModel model = new UrlServiceModel();
		
		ServiceTest<UrlConfiguration,UrlServiceModel> serviceTest = new ServiceTest<UrlConfiguration,UrlServiceModel>(
				"Sample Ping Test","ping","Ping Test",request.getRequestId(),config,model);
		String uri = "port://" + getHost() + ":" + getPort() + "/tcp?delay=" + getDelay();
		System.out.println("URI: " + uri);
        from(uri)
        .setHeader(SERVICE_TEST_INSTANCE).constant(serviceTest)
        .process(new UrlResultToEventProcessor())
        .marshal().serialization()
        .to(getToUri());
	}
}
