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

import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import com.boundary.plugin.sdk.PluginUtil;

public class SnmpTrap implements Serializable {
	
	private static final long serialVersionUID = 13630136686664969L;

	private Vector<VariableBinding> variableBindings;
	
	private String trapName;
	private String host;
	private SnmpVersion version;
	public enum SnmpVersion {V1,V2C,V3};
	private boolean raw;
	
	public SnmpTrap() {
		this.trapName = "";
		this.raw = true;
		this.version = SnmpVersion.V2C;
		this.host = "localhost";
		this.variableBindings = new Vector<VariableBinding>();
	}

	public Vector<VariableBinding> getVariableBindings() {
		return variableBindings;
	}
	
	public VariableBinding [] getVarBinds() {
		return (VariableBinding [])variableBindings.toArray();
	}
	
	public VariableBinding getVarBind(int index) {
		return variableBindings.get(index);
	}
	
	public VariableBinding getVarBind(OID oid) {
		VariableBinding varBind = null;
		
		for (VariableBinding vb : variableBindings) {
			if (vb.getOid() == oid) {
				varBind = vb;
			}
		}
		return varBind;
	}

	public void setVariableBindings(Vector<VariableBinding> variableBindings) {
		for (VariableBinding vb : variableBindings) {
			this.variableBindings.addElement(vb);
		}
		this.variableBindings = variableBindings;
	}

	public String getTrapName() {
		return trapName;
	}

	public void setTrapName(String trapName) {
		this.trapName = trapName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public SnmpVersion getVersion() {
		return version;
	}

	public void setVersion(SnmpVersion version) {
		this.version = version;
	}

	public boolean isRaw() {
		return raw;
	}

	public void setRaw(boolean raw) {
		this.raw = raw;
	}

	@Override
	public String toString() {
		return "SnmpTrap [variableBindings=" + variableBindings + ", trapName="
				+ trapName + ", host=" + host + ", version=" + version
				+ ", raw=" + raw + "]";
	}

}
