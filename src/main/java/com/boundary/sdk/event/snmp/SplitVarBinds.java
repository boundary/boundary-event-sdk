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

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

public class SplitVarBinds {
	
	private static Logger LOG = LoggerFactory.getLogger(SplitVarBinds.class);	
 
    /**
     * The split body method returns something that is iteratable such as a java.util.List.
     *
     * @param body the payload of the incoming message
     * @return a list containing each part splitted
     */
    public List<VariableBinding> splitBody(Vector<? extends VariableBinding> body) {

        List<VariableBinding> answer = new ArrayList<VariableBinding>();
		for (VariableBinding var : body) {
			OID oid = var.getOid();
			Variable variable = var.getVariable();
			LOG.debug("oid: {}, value: {}, syntax: {}",oid,variable.toLong(),variable.getSyntaxString());
			answer.add(var);
		}
        return answer;
    }
     
    /**
     * The split message method returns something that is iteratable such as a java.util.List.
     *
     * @param body the payload of the incoming message
     * @return a list containing each part splitted
     */
    public List<VariableBinding> splitMessage(@Body Vector<? extends VariableBinding> body) {

        List<VariableBinding> answer = new ArrayList<VariableBinding>();
		for (VariableBinding var : body) {
			OID oid = var.getOid();
			Variable variable = var.getVariable();
			LOG.debug("oid: {}, value: {}, syntax: {}",oid,variable.toLong(),variable.getSyntaxString());
			answer.add(var);
		}
        return answer;
    }
}