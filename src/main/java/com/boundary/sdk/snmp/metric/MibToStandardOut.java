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
package com.boundary.sdk.snmp.metric;

import org.snmp4j.smi.OID;

import com.boundary.plugin.sdk.PluginUtil;
import com.snmp4j.smi.SmiModule;
import com.snmp4j.smi.SmiObject;
import com.snmp4j.smi.SmiType;

public class MibToStandardOut extends MibTransformBase {
	
	public MibToStandardOut() {
		
	}

	@Override
	public void transform(SmiModule module, SmiObject object) {
		OID oid = object.getOID();

		if (object.getType() == SmiType.OBJECT_TYPE_SCALAR) {
			if (oid != null) {
				System.out.println("+++++++++++++++++++");
				System.out.printf("name: %s%noid: %s%n", oid,
						oid.toDottedString());
				System.out.printf("SyntaxString: %s%nSyntax: %s%n",
						oid.getSyntaxString(), oid.getSyntax());
				System.out.printf("ObjectName: %s%nType: %s%n",
						object.getObjectName(), object.getType());
				System.out.printf("ObjectName:SNMP.%s.%s%n",module.getModuleName(),PluginUtil.toUpperUnderscore(object.getObjectName(),'.'));
				String smiSyntax = getSmiSyntax(object.getSmiSyntax());
				System.out.printf("SmiSyntax: %s%nReference: %s%n",
						smiSyntax == null ? object.getSmiSyntax()
								: smiSyntax, object.getReference());
				System.out.printf("Description: %s%n",
						object.getDescription());
				System.out.println("-------------------");

			}
		}
	}

	@Override
	public void end() {
	}

}
