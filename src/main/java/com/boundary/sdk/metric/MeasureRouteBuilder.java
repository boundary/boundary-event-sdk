// Copyright 2014-2015 Boundary, Inc.
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
package com.boundary.sdk.metric;

import static org.apache.camel.LoggingLevel.DEBUG;
import static org.apache.camel.LoggingLevel.INFO;

import org.apache.camel.Exchange;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.api.BoundaryAPIRouteBuilder;

public class MeasureRouteBuilder extends BoundaryAPIRouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(MeasureRouteBuilder.class);
	
	private final String DEFAULT_METRIC_API_HOST="premium-api.boundary.com";
	private final String MEASUREMENT_PATH="v1/measurements";
	
	public MeasureRouteBuilder() {
		setHost(DEFAULT_METRIC_API_HOST);
		setPath(MEASUREMENT_PATH);
	}
		
	@Override
	public void configure() {
		String url = getUrl();
		
		// Configure the HTTP endpoint
		setConfiguration();
		
		RouteDefinition routeDefinition = from(fromUri)
			.startupOrder(startUpOrder)
			.routeId(routeId)
			.marshal().json(JsonLibrary.Jackson)
			.log(INFO,"Measurement: ${body}")
			.setHeader(HTTP_AUTHORIZATION,constant(" Basic " + getAuthentication()))
			.setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.to("log:com.boundary.sdk.metric.MeasureRouteBuilder?level=INFO&groupInterval=60000&groupDelay=60000&groupActiveOnly=false")
			.log(INFO, url.toString())
			.log(INFO,"${headers}")
			.to(url.toString())
			.log(DEBUG,"HTTP Method: ${headers.CamelHttpMethod},AcceptContentType={headers.CamelAcceptContentType}")
			.log(INFO,"HTTP Response Code: ${headers.CamelHttpResponseCode},Location: ${headers.Location}")
			;
		if (getToUri() != null) {
			routeDefinition.to(getToUri());
		}
	}
}
