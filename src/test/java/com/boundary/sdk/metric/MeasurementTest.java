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

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public static Measurement read(String resource) throws URISyntaxException {
		Measurement instance = new Measurement();

		ClassLoader classLoader = instance.getClass().getClassLoader();
		URL url = classLoader.getResource(resource);
		File file = new File(url.toURI());

		ObjectMapper mapper = new ObjectMapper();

		try {
			instance = mapper.readValue(file,Measurement.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public static void write(OutputStream out, Measurement measurement)
	{  
	      ObjectMapper mapper = new ObjectMapper();
	      try
	      {
	         mapper.writeValue(out,measurement);
	      } catch (JsonGenerationException e)
	      {
	         e.printStackTrace();
	      } catch (JsonMappingException e)
	      {
	         e.printStackTrace();
	      } catch (IOException e)
	      {
	         e.printStackTrace();
	      }
	   }
	
	@Test
	public void testFromJson() {
		
	}
	
	@Test
	public void testToJson() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Measurement measurement = new Measurement();
		
		measurement.setMeasure(100);
		measurement.setMetric("MY_METRIC");
		measurement.setSource("myhost");
		measurement.setTimestamp(new Date());
		write(out,measurement);
		System.out.println(out);
	}
}
