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
package com.boundary.sdk.event.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceCheckResults {
	
	private List<ServiceTest<?,?>> list = new ArrayList<ServiceTest<?,?>>();

	public ServiceCheckResults() {

	}

	public void add(ServiceTest<?,?> test) {
		list.add(test);
	}
	
	public List<ServiceTest<?,?>> getServiceTests() {
		return this.list;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		for(ServiceTest<?,?> test :list) {
			s.append(test.getName());
		}
		return s.toString();
	}
}
