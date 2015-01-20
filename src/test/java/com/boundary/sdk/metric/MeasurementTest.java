package com.boundary.sdk.metric;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MeasurementTest {
	
    @Produce(uri = "direct:meter-in")
    private ProducerTemplate producerTemplate;
    
    @EndpointInject(uri = "mock:meter-out")
    private MockEndpoint mockOut;
	
	private String expectedString;
	private Measurement measure;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		measure = new Measurement();
		expectedString = "DEADBEEF";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMeasurement() {
		assertEquals("check for empty source","",measure.getSource());
		assertEquals("check for empty metric","",measure.getMetric());
		assertNull("check for null timestamp",measure.getTimestamp());
		assertEquals("check for 0 value for measure",0,measure.getMeasure());
	}

	@Test
	public void testSource() {
		measure.setSource(expectedString);
		assertEquals("check source",expectedString,measure.getSource());
	}

	@Test
	public void testMetric() {
		measure.setMetric(expectedString);
		assertEquals("check metric",expectedString,measure.getMetric());
	}

	@Test
	public void testMeasure() {
		measure.setMeasure(100);
		assertEquals("check measure",100,measure.getMeasure());
	}

	@Test
	public void testTimestamp() {
		Date now = new Date();
		measure.setTimestamp(now);
		assertEquals("check timestamp",now,measure.getTimestamp());
	}

	@Test
	public void testToString() {
		String s = "{\"source\": \"\",\"metric\": \"\",\"measure\": 0}";
		assertEquals("check to string",s,measure.toString());
	}
}
