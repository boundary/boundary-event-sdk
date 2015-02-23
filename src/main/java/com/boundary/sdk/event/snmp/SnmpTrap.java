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

/**
 * Class that encapsulates a received SNMP trap that can be filtered
 * by Esper
 *
 */
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

	/**
	 * Return a vector of all the {@link VariableBinding}s associated
	 * with this trap.
	 * 
	 * @return {@link Vector} of {@link VariableBinding} objects
	 */
	public Vector<VariableBinding> getVariableBindings() {
		return variableBindings;
	}
	
	/**
	 * Returns an array of {@link VariableBinding} objects
	 * @return {@link VariableBinding []}
	 */
	public VariableBinding [] getVarBinds() {
		return (VariableBinding [])variableBindings.toArray();
	}
	
	/**
	 * Return a {@link VariableBinding} by position
	 * @param index Which variable binding to return
	 * @return {@link VariableBinding}
	 */
	public VariableBinding getVarBind(int index) {
		return variableBindings.get(index);
	}
	
	/**
	 * Return a {@link VariableBinding} by {@link OID} instance
	 * @param oid {@link OID} of the {@link VariableBinding} to return
	 * @return {@link VariableBinding} or null if not found
	 */
	public VariableBinding getVarBind(OID oid) {
		VariableBinding varBind = null;
		
		for (VariableBinding vb : variableBindings) {
			if (vb.getOid().toDottedString().equals(oid.toDottedString())) {
				varBind = vb;
			}
		}
		return varBind;
	}
	
	/**
	 * Return the {@link VariableBinding} by the oid values as a {@link String}
	 * 
	 * @param oid {@link String} with {@link OID} to return
	 * @return {@link VariableBinding}
	 */
	public VariableBinding getVarBind(String oid) {
		VariableBinding varBind = null;
		
		for (VariableBinding vb : variableBindings) {
			if (vb.getOid().toDottedString().equals(oid)) {
				varBind = vb;
			}
		}
		return varBind;
	}

	/**
	 * Sets the {@link VariableBinding}s associated with this trap
	 * 
	 * @param variableBindings {@link Vector} of {@link VariableBinding}s
	 */
	public void setVariableBindings(Vector<VariableBinding> variableBindings) {
		for (VariableBinding vb : variableBindings) {
			this.variableBindings.addElement(vb);
		}
		this.variableBindings = variableBindings;
	}

	/**
	 * Returns the name of {@link VariableBinding} that is the NOTIFICATION TYPE
	 * in the {@link VariableBinding} list
	 * 
	 * @return {@link String} Name of the variable binding that is of notification type
	 */
	public String getTrapName() {
		return trapName;
	}

	/**
	 * Set the trap name. Other logic handles setting which {@link VariableBinding} instance
	 * is of NOTIFICATION type.
	 * 
	 * @param trapName {@link String}
	 */
	public void setTrapName(String trapName) {
		this.trapName = trapName;
	}

	/**
	 * Returns the source of the trap
	 * 
	 * @return {@link String} Source host of the trap
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the source host of the trap
	 * 
	 * @param host Name/IP Address of the source of the trap
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Returns the version of this trap
	 * 
	 * @return {@link SnmpVersion}
	 */
	public SnmpVersion getVersion() {
		return version;
	}


	/**
	 * Sets the version of the trap
	 * 
	 * @param version {@link SnmpVersion}
	 */
	public void setVersion(SnmpVersion version) {
		this.version = version;
	}

	/**
	 * Indicates if the Trap is full recognized. NOTE: This implementation
	 * and application specific. It is up the user configuring the integration
	 * to set this field accordingly to suit their purpose.
	 * 
	 * @return {@link boolean} state indicating raw or not
	 */
	public boolean isRaw() {
		return raw;
	}

	/**
	 * Sets if the Trap is fully recognized
	 * @param raw {@link boolean} toggle raw state
	 */
	public void setRaw(boolean raw) {
		this.raw = raw;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SnmpTrap [variableBindings=");
		builder.append(variableBindings);
		builder.append(", trapName=");
		builder.append(trapName);
		builder.append(", host=");
		builder.append(host);
		builder.append(", version=");
		builder.append(version);
		builder.append(", raw=");
		builder.append(raw);
		builder.append("]");
		return builder.toString();
	}
}
