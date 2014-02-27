/**
 * 
 */
package com.boundary.sdk;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.http.HttpComponent;


/**
 * @author davidg
 *
 */
public class BoundarySample {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Fixed values, need to make externally configurable
		String orgID = "3ehRi7uZeeaTN12dErF5XOnRXjC";
		String apiKey = "ARI0PzUzWYUo7GG1OxiHmABTpr9";

		CamelContext context = new DefaultCamelContext();
        //context.addComponent("http", new HttpComponent());

        context.addRoutes(new BoundaryRoute(orgID,apiKey));
        // context.setTracing(true);
        context.start();
        Thread.sleep(10000);
        context.stop();
	}


}
