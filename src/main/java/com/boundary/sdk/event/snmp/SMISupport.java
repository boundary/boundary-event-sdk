package com.boundary.sdk.event.snmp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.SNMP4JSettings;

import com.snmp4j.smi.CompilationResult;
import com.snmp4j.smi.SmiError;
import com.snmp4j.smi.SmiManager;
import com.snmp4j.smi.CompilationMonitor;

/**
 * Provides support SMI (Structured Management Information) for decoding OIDs.
 * 
 * @author davidg
 *
 */
public class SMISupport {
	
	private static Logger LOG = LoggerFactory.getLogger(SMISupport.class);
	
	protected SmiManager smiManager;
	
	private String repositoryPath;
	private String license;
	
	private CompilationMonitor monitor;

	public SMISupport() {
		this.monitor = new MIBCompilerLogger();
	}
	
	/**
	 * Sets the which contains compiled MIBs
	 * @param repositoryPath Directory path (e.g. /home/kermit/mibRepository)
	 */
	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}
	
	/**
	 * Returns the current path to the repository
	 * 
	 * @return
	 */
	public String getRepositoryPath() {
		return this.repositoryPath;
	}
	
	/**
	 * 
	 * @param license
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getLicense() {
		return this.license;
	}
	
	/**
	 * 
	 * @return
	 */
	public SmiManager getSmiManager() {
		return this.smiManager;
	}
	
	public void initialize() {
		try {
			smiManager = new SmiManager(license, new File(repositoryPath));
		    SNMP4JSettings.setOIDTextFormat(smiManager);
		    SNMP4JSettings.setVariableTextFormat(smiManager);

		} catch (IOException e) {
			LOG.error("Failed to initialize SmiManager");
			e.printStackTrace();
		}
		// TBD??
	    // If you need to disable full index formatting, then choose a different format below and uncomment the line:
	    // smiManager.setOidFormat(OIDFormat.ObjectNameAndDecodedIndex4RoundTrip);
	}
	
	/**
	 * Loads all of the modules from the repository
	 */
	public void loadModules() {
		try {
			String[] moduleNames = smiManager.listModules();
			for (String name :moduleNames) {
				smiManager.loadModule(name);
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the compiling of a single MIB, single zip file with MIBs, or a directory of MIBs.
	 * 
	 * TODO: Add support of handling a zip file.??
	 * @param file
	 */
	public void compile(File file) {
		
		LOG.debug("BEGIN MIB COMPILE");

		// Determine if this a single file or a directory of files
		if (file.exists() && file.canRead()) {
			File[] files;
			
			// Generate a list of files
			if (file.isDirectory()) {
				files = file.listFiles();
			} else {
				files = new File[1];
				files[0] = file;
			}
			
			LOG.debug("Compiling " + files.length + " MIB files");
			//
			// Compile MIB files without monitor (null), load them directly into
			// the MIB repository in memory,
			// update existent MIB modules, and do not ignore syntax errors:
			List<CompilationResult> results = null;
			try {
				results = smiManager.compile(files,this.monitor,true, true, false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int ok = 0;
			for (CompilationResult result : results) {
				if (result.getSmiErrorList() == null) {
					ok += result.getModuleNames().size();
				}
			}
			LOG.info("" + file + " contains ");
			LOG.info(" " + ok + " syntactically correct MIB modules and");
			LOG.info(" " + (results.size() - ok) + " MIB modules with errors.");
			String lastFile = null;

			for (CompilationResult result : results) {
				List<SmiError> smiErrors = result.getSmiErrorList();
				String n = result.getFileName();
				n = n.substring(n.lastIndexOf('/') + 1);
				if ((lastFile == null) || (!lastFile.equals(n))) {
					LOG.info("------ " + n + " ------");
					lastFile = n;
				}
				if (smiErrors != null) {
					for (int j = 0; j < smiErrors.size(); j++) {
						SmiError error = smiErrors.get(j);
						String txt = n + " #" + (j + 1) + ": "
								+ error.getMessage();
						LOG.info(txt);
					}
				} else {
					String txt = n + ": " + "OK";
					LOG.info(txt);
				}
			}
		}
		else {
			LOG.error("Cannot access MIB file '" + file.getName() + "'");
		}
		
		LOG.debug("END MIB COMPILE");
	}
	public void compile(String fname) throws IOException {
		//super(new File(fname));
	}
}
