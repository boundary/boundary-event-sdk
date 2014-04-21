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
