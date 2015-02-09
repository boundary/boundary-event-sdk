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
import java.util.List;

import org.snmp4j.smi.OID;

import com.boundary.plugin.sdk.MetricAggregate;
import com.boundary.plugin.sdk.MetricUnit;
import com.boundary.plugin.sdk.PluginUtil;
import com.snmp4j.smi.SmiModule;
import com.snmp4j.smi.SmiObject;
import com.snmp4j.smi.SmiType;

public class MibToOidMap extends MibTransformBase {
	
	OidMapList oidMapList;
	
	List<OidMapEntry> oidMapEntryList;

	private OidMapEntry oidMapEntry;

	private ArrayList<OidMap> oidList;
	
	public MibToOidMap() {

	}

	@Override
	public void transform(SmiModule module, SmiObject object) {

		OID oid = object.getOID();
		if (standardFilterCriteria(module,object)) {
			if (oid != null) {
				OidMap oidMap = new OidMap();
				String objectName = object.getObjectName();
				String moduleName = module.getModuleName();
				String metricId = oidToMetricId(moduleName,objectName);
				String description = oidToDescription(object.getDescription());
				String name = oidToDisplayName(moduleName,objectName);
				String oidStr = oidToGetOid(oid);
				
				oidMap.setOid(oidStr);
				oidMap.setMetricId(metricId);
				oidMap.setName(name);
				oidMap.setDescription(description);
				oidList.add(oidMap);
				
			} else {
				System.err.printf("oid is null: %s%n",object.getObjectName());
			}
		} 
	}
	
	@Override
	public void beginTransform() {
		oidMapList = new OidMapList();
		oidMapEntryList = new ArrayList<OidMapEntry>();
	}


	@Override
	public void endTransform() {
		oidMapList.setOidList(oidMapEntryList);
		convertToJson(oidMapList);
	}

	@Override
	public void beginModule(SmiModule module) {
		oidMapEntry = new OidMapEntry();
		oidMapEntry.setId(oidMapList.getNextId());
		oidMapEntry.setName(module.getModuleName());
		oidList = new ArrayList<OidMap>();
	}

	@Override
	public void endModule(SmiModule module) {
		oidMapEntry.setOids(oidList);
		oidMapEntryList.add(oidMapEntry);
	}
}
