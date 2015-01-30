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

import org.snmp4j.PDU;

public class SnmpMessageEvent implements Serializable {
	
	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5737757303082287318L;
	private PDU pdu;
	
	public SnmpMessageEvent(PDU pdu) {
		this.pdu = pdu;
	}
	
	public PDU getSnmpMessage() {
		return this.pdu;
	}
}
