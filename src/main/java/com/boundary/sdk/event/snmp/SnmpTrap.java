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
package com.boundary.sdk.event.snmp;

import java.io.Serializable;
import java.util.Vector;

import org.snmp4j.smi.VariableBinding;

public class SnmpTrap implements Serializable{
	
	
	private Vector<? extends VariableBinding> variableBindings;
	
	private String trapName;
	
	public SnmpTrap() {
		
	}

	public Vector<? extends VariableBinding> getVariableBindings() {
		return variableBindings;
	}

	public void setVariableBindings(Vector<? extends VariableBinding> variableBindings) {
		this.variableBindings = variableBindings;
	}

	public String getTrapName() {
		return trapName;
	}

	public void setTrapName(String trapName) {
		this.trapName = trapName;
	}
}
