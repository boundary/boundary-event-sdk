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
package com.boundary.sdk.event.script;

import java.util.HashMap;
import java.util.Map;

import com.boundary.sdk.event.snmp.SnmpPollerConfiguration;
import static com.boundary.sdk.event.snmp.SnmpPollerRouteBuilder.*;

public class ScriptTestUtils {

	public static Map<String,Object> setScriptHeader(String script) {
		Map <String,Object> headers = new HashMap<String,Object>();
		headers.put("CamelLanguageScript",script);
		return headers;
	}
	
	public static Map<String,Object> setScriptHeaders(String script,SnmpPollerConfiguration config) {
		Map <String,Object> headers = new HashMap<String,Object>();
		headers.put("CamelLanguageScript",script);
		headers.put(BOUNDARY_SNMP_POLLER_CONFIG,config);

		return headers;
	}
}
