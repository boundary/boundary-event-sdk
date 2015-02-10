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

import java.util.ArrayList;

import org.snmp4j.smi.OID;

import com.boundary.plugin.sdk.PluginUtil;
import com.boundary.sdk.plugin.Metrics;
import com.snmp4j.smi.SmiModule;
import com.snmp4j.smi.SmiObject;

public class MibToPluginMetrics extends MibTransformBase {
	
	private Metrics metrics;
	private ArrayList<String> metricList;

	public MibToPluginMetrics() {
		
	}

	@Override
	public void transform(SmiModule module, SmiObject object) {
		OID oid = object.getOID();

		if (standardFilterCriteria(module,object)) {
			if (oid != null) {
				String objectName = object.getObjectName();
				String moduleName = module.getModuleName();
				String metricId = oidToMetricId(moduleName,objectName);
				metricList.add(metricId);
			}
		}
	}
	
	@Override
	public void beginTransform() {
		metrics = new Metrics();
		metricList = new ArrayList<String>();
	}
	
	@Override
	public void endTransform() {
		metrics.setMetrics(metricList);
		convertToJson(metrics);
	}

	@Override
	public void beginModule(SmiModule module) {
	}

	@Override
	public void endModule(SmiModule module) {
	}
}
