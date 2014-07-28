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
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * @author davidg
 *
 */
public class ServiceCheckAggregate implements AggregationStrategy {

	/**
	 * 
	 */
	public ServiceCheckAggregate() {

	}

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Exchange returnExchange = null;
		Message message = newExchange.getIn();
		ServiceTest<?,?> test = message.getHeader(
				ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE,
				ServiceTest.class);
		
		if (oldExchange == null) {
			ServiceCheckResults results = new ServiceCheckResults();
			results.add(test);
			newExchange.getIn().setBody(results);
			returnExchange = newExchange;
		}
		else {
			ServiceCheckResults results = oldExchange.getIn().getBody(ServiceCheckResults.class);
			results.add(test);
			returnExchange = oldExchange;
		}
		return returnExchange;
	}
}
