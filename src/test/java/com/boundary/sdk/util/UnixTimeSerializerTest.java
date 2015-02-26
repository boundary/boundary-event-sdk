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
package com.boundary.sdk.util;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.sdk.metric.Measurement;
import com.boundary.sdk.metric.MyDate;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UnixTimeSerializerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	public static MyDate read(String resource) throws URISyntaxException {
		MyDate instance = new MyDate();

		ClassLoader classLoader = instance.getClass().getClassLoader();
		URL url = classLoader.getResource(resource);
		File file = new File(url.toURI());

		ObjectMapper mapper = new ObjectMapper();

		try {
			instance = mapper.readValue(file,MyDate.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public static void write(OutputStream out,MyDate measurement)
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
		MyDate myDate = new MyDate();
		write(out,myDate);
		System.out.println(out);
	}

}
