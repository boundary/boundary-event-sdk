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
package com.boundary.sdk.event.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SelectListConverterTest {

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

	@Test
	public void test() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map map = new HashMap<String,Object>();
		map.put("red", new String("1"));
		map.put("blue",new String("2"));
		map.put("green",new String("3"));
		
		list.add(map);
		list.add(map);
		
		Iterator i = SelectListConverter.toIterator(list);
		
		while (i.hasNext()){
			System.out.println(map);
			i.next();
		}
// TODO: Apply proper test to validate the conversion		
//		assertEquals("check class",java.util.Iterator.class,i.getClass());
	}

}
