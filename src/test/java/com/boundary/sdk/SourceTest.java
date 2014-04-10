package com.boundary.sdk;

import static org.junit.Assert.*;

import org.junit.Test;

import com.boundary.sdk.Source;

import java.util.HashMap;
import java.util.Map;

public class SourceTest {
	
	@Test
	public void testDefaultConstructor() {
		Source s = new Source();
		
		assertNotNull(s.getRef());
		assertNotNull(s.getType());
		assertNotNull(s.getProperties());
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
		Map<String,Object> expectedProperties = new HashMap<String,Object>();
		Source s = new Source();
		expectedProperties.put("foo", "bar");
		s.setProperties(expectedProperties);
		assertEquals(s.getProperties(),expectedProperties);
		
	}


}
