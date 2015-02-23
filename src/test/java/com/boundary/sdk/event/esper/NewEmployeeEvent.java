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
import java.util.Map.Entry;

public class NewEmployeeEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8757562061344149582L;
	Employee employee;

	NewEmployeeEvent(Employee employee) {
		this.employee = employee;
	}

	public NewEmployeeEvent(String firstName,
			Map<String,Address> addresses) {
		for (Entry<String, Address> entry : addresses.entrySet() ) {
			addresses.put(entry.getKey(), entry.getValue());
		}
	}
	
	public String getName() {
		return this.employee.getName();
	}

	public Address getAddress(String type) {
		return employee.getAddresses().get(type);
	}

	public Employee getSubordinate(int index) {
		return employee.getSubordinate(index);
	}

	public Employee[] getAllSubordinates() {
		Employee[] subordinates = new Employee[1];
		return employee.getAllSubordinates();
	}
	
	public Iterable<EducationHistory> getEducation() {
		return employee.getEducation();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NewEmployeeEvent [employee=");
		builder.append(employee);
		builder.append("]");
		return builder.toString();
	}
}
