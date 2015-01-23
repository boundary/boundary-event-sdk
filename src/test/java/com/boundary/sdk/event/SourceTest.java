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
package com.boundary.sdk.event;

import static org.junit.Assert.*;

import org.junit.Test;

import com.boundary.sdk.event.Source;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SourceTest {
	
	@Test
	public void testDefaultConstructor() {
		Source s = new Source();
		
		assertNull(s.getRef());
		assertNull(s.getType());
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
		LinkedHashMap<String,Object> expectedProperties = new LinkedHashMap<String,Object>();
		Source s = new Source();
		expectedProperties.put("foo", "bar");
		s.setProperties(expectedProperties);
		assertEquals(s.getProperties(),expectedProperties);
	}
	
	@Test
	public void testDefaultToString() {
		Source s = new Source();
		assertNotNull(s);
		assertNotNull(s.toString());
		assertEquals("",s.toString());
	}
	
	@Test
	public void testEqual1() {
		Source source1 = new Source("foobar","red","green");
		Source source2 = new Source("foobar","red","green");
		
		assertTrue("test equal",source1.equals(source2));
	}
	
	@Test
	public void testNotEqual() {
		Source source1 = new Source("foobar","red");
		Source source2 = new Source("barfoo","der");
		
		assertFalse("test equal",source1.equals(source2));
	}
	
	@Test
	public void testNotEqual2() {
		Source source1 = new Source("foobar","red","green");
		Source source2 = new Source("barfoo","der","neerg");

		assertFalse("test not equal2",source1.equals(source2));
	}
}
