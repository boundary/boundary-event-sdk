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

import org.snmp4j.smi.SMIConstants;

public abstract class MibTransformBase implements MibTransform {

	protected String getSmiSyntax(int syntax) {
		String s = null;
		
		switch(syntax) {
		case SMIConstants.EXCEPTION_NO_SUCH_INSTANCE:
			s = "EXCEPTION_NO_SUCH_INSTANCE";
			break;
		case SMIConstants.EXCEPTION_END_OF_MIB_VIEW:
			s = "EXCEPTION_END_OF_MIB_VIEW";
			break;
			
		case SMIConstants.EXCEPTION_NO_SUCH_OBJECT:
			s = "EXCEPTION_NO_SUCH_OBJECT";
			break;
//		case SMIConstants.SYNTAX_BITS:
//			s = "SYNTAX_BITS";
//			break;
		case SMIConstants.SYNTAX_COUNTER32:
			s = "SYNTAX_COUNTER32";
			break;
		case SMIConstants.SYNTAX_COUNTER64:
			s = "SYNTAX_COUNTER64";
			break;
		case SMIConstants.SYNTAX_GAUGE32:
			s = "SYNTAX_GAUGE32";
			break;
		case SMIConstants.SYNTAX_INTEGER32:
			s = "SYNTAX_INTEGER32";
			break;
		case SMIConstants.SYNTAX_IPADDRESS:
			s = "SYNTAX_IPADDRESS";
			break;
		case SMIConstants.SYNTAX_NULL:
			s = "SYNTAX_NULL";
			break;
		case SMIConstants.SYNTAX_OBJECT_IDENTIFIER:
			s = "SYNTAX_OBJECT_IDENTIFIER";
			break;
		case SMIConstants.SYNTAX_OCTET_STRING:
			s = "SYNTAX_OCTET_STRING";
			break;
		case SMIConstants.SYNTAX_OPAQUE:
			s = "SYNTAX_OPAQUE";
			break;
		case SMIConstants.SYNTAX_TIMETICKS:
			s = "";
			break;
//		case SMIConstants.SYNTAX_UNSIGNED_INTEGER32:
//			s = "";
//			break;
		}
		
		return s;
	}

}
