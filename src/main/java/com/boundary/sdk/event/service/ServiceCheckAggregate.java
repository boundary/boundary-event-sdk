/**
 * 
 */
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
