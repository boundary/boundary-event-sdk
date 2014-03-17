package com.boundary.sdk;

import static org.junit.Assert.*;

import org.junit.Test;

import com.boundary.sdk.Source;

import java.util.HashMap;
import java.util.Map;

public class TestSource {
	
	@Test
	public void testDefaultConstructor() {
		Source s = new Source();
		
		assertNull(s.getRef());
		assertNull(s.getType());
		assertNull(s.getProperties());
	}

	@Test
	public void testRef() {
		String expectedRef = "foo";
		Source s = new Source();
		s.setRef(expectedRef);
		assertEquals(s.getRef(),expectedRef);
	}
	
	@Test
	public void testType() {
		String expectedType = "bar";
		Source s = new Source();
		s.setType(expectedType);
		assertEquals(s.getType(),expectedType);
	}
	
	@Test
	public void testProperties() {
		Map<String,String> expectedProperties = new HashMap<String,String>();
		Source s = new Source();
		expectedProperties.put("foo", "bar");
		s.setProperties(expectedProperties);
		assertEquals(s.getProperties(),expectedProperties);
		
	}


}
