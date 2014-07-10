package com.boundary.sdk.event.service.db;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

public class ServiceCheckQueryTest extends CamelTestSupport {
	
	Map<String,Object> map;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Create scaffolding to test translation into a query
		map = new HashMap<String,Object>();
		map.put("serviceCheckId","99");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore("No test implemented yet")
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:test")
                .id("TestSyslogToEvent")
                .process(new ServiceCheckQuery())
                .log("We got incoming message containing: ${body}")
                   .to("mock:test");
                
            }
        };
    }


}
