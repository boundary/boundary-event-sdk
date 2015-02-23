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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7096129105144914284L;
	List<EducationHistory> educationHistory;
	Map<String, Address> addresses;
	List<Employee> subordinates;
	String name;
	
	public Employee(String name) {
		this.name = name;
		this.subordinates = new ArrayList<Employee>();
		this.addresses = new HashMap<String, Address>();
	}

	public Map<String, Address> getAddresses() {
		return addresses;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAddress(String type,Address address) {
		addresses.put(type,address);
	}
	
	public Iterable<EducationHistory> getEducation() {
		return educationHistory;
	}
	
	public void addSubordinate(Employee subordinate) {
		subordinates.add(subordinate);
	}
	
	public Employee getSubordinate(int index) {
		return this.subordinates.get(index);
	}

	public Employee[] getAllSubordinates() {
		return (Employee [])this.subordinates.toArray();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Employee [educationHistory=");
		builder.append(educationHistory);
		builder.append(", addresses=");
		builder.append(addresses);
		builder.append(", subordinates=");
		builder.append(subordinates);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
}
