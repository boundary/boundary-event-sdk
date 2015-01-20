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

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.camel.component.url.UrlConfiguration;
import com.boundary.sdk.event.service.ping.PingServiceModel;
import com.boundary.sdk.event.service.port.PortServiceModel;
import com.boundary.sdk.event.service.ssh.SshxServiceModel;
import com.boundary.sdk.event.service.url.UrlServiceModel;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;

public class ExtractServiceTestConfiguration {

	public PingConfiguration getPingConfiguration(ServiceTest<PingConfiguration,PingServiceModel> serviceTest) {
		return serviceTest.getConfiguration();
	}
	
	/**
	 * Extract the {@link PortConfiguration} from the {@link ServiceTest} and
	 * set as header in {@link Message}
	 * @param exchange {@link Exchange} from Camel route
	 */
	public void extractPortConfiguration(Exchange exchange) {
		Message message = exchange.getIn();
		ServiceTest<PortConfiguration,PortServiceModel> serviceTest = message.getBody(ServiceTest.class);
		PortConfiguration configuration = serviceTest.getConfiguration();
		
		message.setHeader(SERVICE_TEST_INSTANCE, serviceTest);
		message.setBody(configuration);
	}
	
	/**
	 * Extract the {@link PingConfiguration} from the {@link ServiceTest} and
	 * set as header in {@link Message}
	 * @param exchange {@link Exchange} from Camel route
	 */
	public void extractPingConfiguration(Exchange exchange) {
		Message message = exchange.getIn();
		ServiceTest<PingConfiguration,PortServiceModel> serviceTest = message.getBody(ServiceTest.class);
		PingConfiguration configuration = serviceTest.getConfiguration();
		
		message.setHeader(SERVICE_TEST_INSTANCE, serviceTest);
		message.setBody(configuration);
	}
	
	/**
	 * Extract the {@link SshxConfiguration} from the {@link ServiceTest} and
	 * set as header in {@link Message}
	 * @param exchange {@link Exchange} from Camel route
	 */
	public void extractSshxConfiguration(Exchange exchange) {
		Message message = exchange.getIn();
		ServiceTest<SshxConfiguration,SshxServiceModel> serviceTest = message.getBody(ServiceTest.class);
		SshxConfiguration configuration = serviceTest.getConfiguration();
		
		message.setHeader(SERVICE_TEST_INSTANCE, serviceTest);
		// TODO: Set the host header to the host name so we can route to the correct SSH
		//       end point. Once there is a SSH component that can handle being a producer then
		//       this work around can be eliminated
		message.setHeader("host",configuration.getHost());
		message.setBody(configuration);
	}
	
	/**
	 * Extract the {@link UrlConfiguration} from the {@link ServiceTest} and
	 * set as header in {@link Message}
	 * @param exchange {@link Exchange} from Camel route
	 */
	public void extractUrlConfiguration(Exchange exchange) {
		Message message = exchange.getIn();
		ServiceTest<UrlConfiguration,UrlServiceModel> serviceTest = message.getBody(ServiceTest.class);
		UrlConfiguration configuration = serviceTest.getConfiguration();
		
		message.setHeader(SERVICE_TEST_INSTANCE, serviceTest);
		message.setBody(configuration);
	}
}
