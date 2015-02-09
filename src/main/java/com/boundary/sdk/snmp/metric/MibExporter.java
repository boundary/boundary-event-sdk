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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.snmp.SmiSupport;
import com.snmp4j.smi.SmiManager;
import com.snmp4j.smi.SmiModule;
import com.snmp4j.smi.SmiObject;

public class MibExporter extends SmiSupport {
	
	
	private static Logger LOG = LoggerFactory.getLogger(MibExporter.class);
	
	public static enum ExportType {METRIC,OID_MAP,OUT};
	private ExportType exportType;
	private List<String> moduleList;
	private boolean verbose;
	
	
	public MibExporter() {
		moduleList = new ArrayList<String>();
		verbose = false;
	}


	public void addModule(String moduleName) {
		moduleList.add(moduleName);
	}
	
	public MibTransform getTransform() {
		MibTransform transform = null;
		switch(this.exportType) {
		case METRIC:
			transform = new MibToMetricDefinition();
			break;
		case OID_MAP:
			transform = new MibToOidMap();
			break;
		case OUT:
			transform = new MibToStandardOut();
			break;
		}
		return transform;
	}

	public void export() {

		this.initialize();
		SmiManager smiManager = this.getSmiManager();
		MibTransform transform = this.getTransform();
		
		//
		// Loop through the module names, load, loop through objects
		// and call the transform
		//
		for (String moduleName : moduleList) {
			// Load the module
			smiManager.loadModule(moduleName);
			
			// Find the root object
			SmiObject root = smiManager.findRootSmiObject();
			if (this.verbose) {
				LOG.info("Root Oid: {}",root.getOID());
			}
			SmiModule module = smiManager.findSmiModule(moduleName);
			if (this.verbose) {
				LOG.info(module.getModuleName());
			}
			List<String> objectList = module.getObjectNames();
			for (String objectName : objectList) {
				SmiObject object = smiManager.findSmiObject(module.getModuleName(), objectName);
				transform.transform(module,object);
			}
			smiManager.unloadModule(moduleName);
		}
		
		transform.end();
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	public void setExportType(ExportType exportType) {
		this.exportType = exportType;
	}
}
