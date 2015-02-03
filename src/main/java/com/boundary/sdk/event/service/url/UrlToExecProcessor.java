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
package com.boundary.sdk.event.service.url;

import static com.boundary.sdk.event.util.PropertyUtils.getProperties;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_ARGS;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_EXECUTABLE;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.url.UrlResult;


public class UrlToExecProcessor implements Processor {
	
	private String command = "metric-add";
	
    private static final Logger LOG = LoggerFactory.getLogger(UrlToExecProcessor.class);

	/**
	 * Handles the translation of a {@link UrlResult} to headers needed to run Camel exec
	 * 
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		UrlResult result = message.getHeader("url-result", UrlResult.class);
				
		List<String> args = new ArrayList<String>();

		// Add the source
		args.add(result.getHost());
			
		// Add the metric name
		String metricName = getMetricName(result.getHost());
		args.add(metricName);

		// Add the measure
		args.add(Long.toString(result.getResponseTime()));
		
		message.setHeader(EXEC_COMMAND_EXECUTABLE, command);
		message.setHeader(EXEC_COMMAND_ARGS,args);

		message.setBody("");
	}
	
	private static Properties metricNameMap = new Properties();
	
	protected static String getMetricName(String host) {
		getProperties(metricNameMap,"url.metric.names.properties");
		return metricNameMap.getProperty(host,"UNKNOWN");
	}

}
