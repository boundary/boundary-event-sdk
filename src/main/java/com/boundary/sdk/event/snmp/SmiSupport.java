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
public class SmiSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(SmiSupport.class);
	
	protected SmiManager smiManager;
	
	private String repositoryPath;
	private String license = null;
	
	private CompilationMonitor monitor;

	public SmiSupport() {
		this.monitor = new MIBCompilerLogger();
	}
	
	/**
	 * Sets the which contains compiled MIBs
	 * @param path Directory path (e.g. /home/kermit/mibRepository)
	 */
	public void setRepository(String path) {
		this.repositoryPath = path;
	}
	
	/**
	 * Returns the current path to the repository
	 * 
	 * @return {@link String}
	 */
	public String getRepository() {
		return this.repositoryPath;
	}
	
	/**
	 * Sets the SNMP4j-SMI license for decoding OIDs
	 * If the license parameter is an empty string then
	 * the license is set to null so SNMP4J-SMI will handle
	 * non-enterprise OID branch, otherwise it will fail on all
	 * calls to decode OIDs. We construct this behavior so that
	 * in spring files we can specify null as the empty string.
	 * 
	 * @param license
	 */
	public void setLicense(String license) {
		this.license = license.isEmpty() ? null : license;
	}
	
	
	/**
	 * Returns the SNMP4J-SMI license.
	 * @return {@link String}
	 */
	public String getLicense() {
		// If the license is null then translate to empty string.
		return this.license == null ? "" : this.license;
	}
	
	/**
	 * 
	 * @return {@link SmiManager}
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
	 * @param file {@link File}
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
	
	/**
	 * 
	 * @param fname {@link String}
	 * @throws IOException
	 */
	public void compile(String fname) throws IOException {
		//super(new File(fname));
	}
}
