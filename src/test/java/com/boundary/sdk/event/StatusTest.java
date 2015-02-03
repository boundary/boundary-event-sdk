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

import com.boundary.sdk.event.Status;

import org.junit.Test;

public class StatusTest {
	
	
	@Test
	public void testACKNOWLEDGED() {
		String s = "ACKNOWLEDGED";
		Status status = Status.fromString(s);
		assertTrue("Test OK status",status.toString() == s);
	}


	@Test
	public void testOK() {
		String s = "OK";
		Status status = Status.fromString(s);
		assertTrue("Test OK status",status.toString() == s);
	}
}
