package com.boundary.sdk;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @version $Revision$
 */
public class RawEventToBoundaryEvent extends CamelTestSupport {
	
    @Produce(uri = TEST_IN)
    private ProducerTemplate producerTemplate;

	
	private static final String TEST_IN="direct:in";
	
	@Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected RouteBuilder[] createRouteBuilders() throws Exception {
    	RouteBuilder[] routes = new RouteBuilder[2];
    	
    	// Create the Boundary Event Route
		BoundaryEventRoute boundary = new BoundaryEventRoute();
		// Configure our properties
		
		routes[0] = boundary;
		
		
		routes[1] = new RouteBuilder(){
            @Override
            public void configure() throws Exception {
                from(TEST_IN)
                .marshal().serialization()
                .to(BoundaryRouteBuilder.DEFAULT_EVENT_TO_URI);
            }
        };
        
        return routes;
    }

    @Test
    public void testDefaultEvent() throws Exception {
    	RawEvent event = RawEvent.getDefaultEvent();
    	
    	producerTemplate.sendBody(event);

        Thread.sleep(2000);     
    }

}
