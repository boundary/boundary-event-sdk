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
package com.boundary.sdk.event.esper;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Calendar.Builder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QueryListTest {
	
	private final String QUERY_LIST_FILE="test-esper-query-load.json";
	
	private QueryList queryList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		load();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private void load() throws Exception {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL url = classLoader.getResource(QUERY_LIST_FILE);
		File file = new File(url.toURI());

		ObjectMapper mapper = new ObjectMapper();

		try {
			this.queryList = mapper.readValue(file, QueryList.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadURIClasspath() throws IOException, URISyntaxException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL url = classLoader.getResource(QUERY_LIST_FILE);
		File file = new File(url.toURI());
		assertTrue("check if file",file.isFile());
		assertTrue("check file exists",file.exists());
	}

	@Test
	public void test() {
		Query query1 = queryList.getQueries().get(0);
		Builder build1 = new Calendar.Builder();
		build1.setTimeZone(TimeZone.getTimeZone("GMT"));
		build1.setDate(2015,0,27);
		build1.setTimeOfDay(0, 0, 0, 0);
		Calendar cal1 = build1.build();
		assertEquals("check query 1 name","red",query1.getName());
		assertEquals("check query 1 create",cal1.getTime(),query1.getCreated());
		assertEquals("check query 1 modified",cal1.getTime(),query1.getModified());
		assertEquals("check query 1 enabled",true,query1.isEnabled());
		assertEquals("check query 1 description","Red description",query1.getDescription());
		assertEquals("check query 1 query",
				"select * from Red where name = 'ONE' and property = 2",query1.getQuery());
		
		Query query2 = queryList.getQueries().get(1);
		Builder build2 = new Calendar.Builder();
		build2.setTimeZone(TimeZone.getTimeZone("GMT"));
		build2.setDate(1992,5,20);
		build2.setTimeOfDay(0, 0, 0, 0);
		Calendar cal2 = build2.build();
		assertEquals("check query 2 name","blue",query2.getName());
		assertEquals("check query 2 create",cal2.getTime(),query2.getCreated());
		assertEquals("check query 2 modified",cal2.getTime(),query2.getCreated());
		assertEquals("check query 2 enabled",false,query2.isEnabled());
		assertEquals("check query 2 description","Blue description",query2.getDescription());
		assertEquals("check query 2 query",
				"select * from Blue where name = 'ONE' and property = 2",query2.getQuery());

		Query query3 = queryList.getQueries().get(2);
		Builder build3 = new Calendar.Builder();
		build3.setTimeZone(TimeZone.getTimeZone("GMT"));
		build3.setDate(2000,11,10);
		build3.setTimeOfDay(0, 0, 0, 0);
		Calendar cal3 = build3.build();
		assertEquals("check query 3 name","green",query3.getName());
		assertEquals("check query 3 create",cal3.getTime(),query3.getCreated());
		assertEquals("check query 3 enabled",true,query3.isEnabled());
		assertEquals("check query 3 description","Green description",query3.getDescription());
		assertEquals("check query 3 query",
				"select * from Green where name = 'ONE' and property = 2",query3.getQuery());
		
	}

}
