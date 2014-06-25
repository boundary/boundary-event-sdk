package com.boundary.sdk.event.util;

import static org.junit.Assert.*;

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
