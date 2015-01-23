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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snmp4j.smi.CompilationMonitor;

/**
 * 
 * Class that implements the {@link CompilationMonitor} interface that
 * monitors that status of the compilation of MIBs.
 * 
 * @author davidg
 *
 */
public class MIBCompilerLogger implements CompilationMonitor {
	
	private static Logger LOG = LoggerFactory.getLogger(MIBCompilerLogger.class);

	public MIBCompilerLogger() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean compilationProgress(String arg0, int arg1, int arg2) {
		String logEntry = String.format("moduleName: %s, current = %d, maxCount=%d",arg0,arg1,arg2);
		LOG.info(logEntry);
		return true;
	}

	@Override
	public boolean loadingProgress(String arg0, int arg1, int arg2) {
		LOG.info(String.format("fileName: %s, current = %d, maxCount=%d",arg0,arg1,arg2));
		return true;
	}

	@Override
	public boolean sortingProgress(String arg0, int arg1, int arg2) {
		LOG.info(String.format("fileName: %s, current = %d, maxCount=%d",arg0,arg1,arg2));
		return true;
	}
}
