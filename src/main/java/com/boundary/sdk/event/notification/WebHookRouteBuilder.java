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
package com.boundary.sdk.event.notification;

import static org.apache.camel.LoggingLevel.*;

import java.io.InputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.component.http.HttpMessage;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.stream.InputStreamCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.camel.Processor;

import com.boundary.sdk.event.BoundaryRouteBuilder;

/**
 *
 */
public class WebHookRouteBuilder extends BoundaryRouteBuilder {

	private static Logger LOG = LoggerFactory.getLogger(WebHookRouteBuilder.class);

	@Override
	public void configure() throws Exception {
		String s = null;
		
		// Create an instance of JacksonDataFormat to convert to JSON to
		// a {@link Notification} instance
		JacksonDataFormat format = new JacksonDataFormat();
		format.setUnmarshalType(Notification.class);
		from(getFromUri())
		.routeId(getRouteId())
		.startupOrder(getStartUpOrder())
//		.log(DEBUG,"headers: ${headers}")
//		.log(DEBUG,"body: ${body}")
//		.log(DEBUG,"body: ${body}")
//		.log(DEBUG,"class: ${body.getClass.toString}")
//		.process(new Processor() {
//					public void process(Exchange exchange) throws Exception {
//						HttpMessage msg = exchange.getIn(HttpMessage.class);
//
//						InputStreamCache sis = msg.getBody(InputStreamCache.class);
//
//						String s = exchange.getContext().getTypeConverter().convertTo(String.class, sis);
//						LOG.debug("process body: " + s);
//
//						Message newMessage = new DefaultMessage();
//						newMessage.setHeaders(msg.getHeaders());
//						newMessage.setBody(s);
//						exchange.setIn(newMessage);
//					}
//		})
//		.log(DEBUG,"BEFORE CLASS: ${body.getClass.toString}")
//		.log(DEBUG,"BEFORE SIZE: ${body.length}")
//		.log(DEBUG,"BEFORE BODY: ${body}")
//		.unmarshal().string("UTF-8")
//		.log(DEBUG,"AFTER CLASS: ${body.getClass.toString}")
//		.log(DEBUG,"AFTER SIZE: ${body.length}")
//		.log(DEBUG,"AFTER BODY: ${body}")
		.unmarshal().json(JsonLibrary.Jackson,Notification.class)
		.to(getToUri());
	}
}
