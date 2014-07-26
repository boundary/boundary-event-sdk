// Copyright 2014 Boundary, Inc.
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


import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Command line tool to compile MIBs
 * 
 * @author davidg
 * 
 */
public class MIBCompiler extends SmiSupport {

	private static Logger LOG = LoggerFactory.getLogger(MIBCompiler.class);
	
	public static final String COMMAND_NAME="boundary.tools.command.name";
	public static final String LICENSE="boundary.tools.snmp.license";
	
	private static String license;
	
	private Options options = new Options();
	private Option repoDirOption;
	private Option mibDirOption;
	private Option licenseOption;
	private Option helpOption;
	private Option strictCompileOption;
	private Option silentOption;
	private Option updateExisting;
	
	// Used to store the name of the wrapper script that is called
	String commandName;
	
	private CommandLine cmd;
	
	private boolean DEBUG;

	private String mibTargetDir;
	private String mibPath;

	/**
	 * Default constructor
	 */
	public MIBCompiler() {
		options = new Options();
		commandName = this.getClass().toString();
		commandName = System.getProperty(COMMAND_NAME);
		if (commandName == null) {
			commandName = this.getClass().toString();
		}
		license = System.getProperty(LICENSE);
		DEBUG=false;
		mibTargetDir="";
		mibPath="";
	}
	
	/**
	 * Outputs help for the command and its options.
	 */
	private void usage() {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(this.commandName, this.options);
		System.exit(0);
	}
	
	/**
	 * Handles all the options passed to the command line.
	 * 
	 * @param args Command line arguments
	 */
	@SuppressWarnings("static-access")
	public void parseCommandLineOptions(String[] args) {
		
		for (String s :args) {
			LOG.debug(s);
		}

		helpOption = OptionBuilder
				.withDescription("Display help information")
				.withLongOpt("help")
				.create("h");
		licenseOption = OptionBuilder.withArgName("license")
				.hasArgs()
				.withValueSeparator(' ')
				.withDescription("The license key string you received with the purchase a SNMP4J-SMI license or its evaluation."
						+ "You may provide leave empty, but then you may not use any MIB modules of the \"enterprise\" OID subtree.")
				.withLongOpt("license")
				.create("l");
		mibDirOption = OptionBuilder.withArgName("mib_path")
				.hasArg()
				.withDescription("Path to single MIB, ZIP file of MIB(s), or a directory of MIBs to compile.")
				.withLongOpt("mib-path")
				.create("m");
		silentOption = OptionBuilder
				.withDescription("Quiet mode, suppress normal output.")
				.withLongOpt("quiet")
				.create("q");
		repoDirOption = OptionBuilder.withArgName("repository_dir")
				.hasArg()
				.withDescription("An empty directory where the "
				+ commandName + "can read and store compiled MIB modules persistently. "  + 
						"Directory must not contain any plain text (i.e., uncompiled) MIB files.")
				.withLongOpt("repository-dir")
				.create("r");
		strictCompileOption = OptionBuilder.withArgName("lenient|standard")
				.hasArg()
				.withDescription("Strictness defines the number of syntax checks done on the sources.")
				.withLongOpt("compiler-strict")
				.create("s");
		updateExisting = OptionBuilder
		        .withDescription("Update existing MIBs in repository")
				.withLongOpt("update-existing")
				.create("u");

		options.addOption(repoDirOption);
		options.addOption(mibDirOption);
		options.addOption(licenseOption);
		options.addOption(helpOption);
		options.addOption(strictCompileOption);
		options.addOption(silentOption);

		try {
			CommandLineParser parser = new BasicParser();
			cmd = parser.parse(options, args);

			if (cmd.hasOption("h") == true && cmd.hasOption("q") == false) {
				usage();
			}
		} catch (Exception e) {
			usage();
		}
	}
	
	/**
	 * Sets how strict the MIB compiler will tolerate
	 * non-standard syntax
	 */
	private void setCompilerStrictness() {
		if (cmd.hasOption("s")) {
			String value = cmd.getOptionValue("s");
			if (value.equals("lenient")) {
				setCompileLeniently(true);
			} else {
				if (value.equals("standard")) {
					setCompileLeniently(false);
				}
			}
		}
	}
	/**
	 * Get the compiler license from the command
	 * The license itself can has spaces, which we have
	 * to add back from the arguments.
	 */
	private void getCompilerLicense() {
		String[] args = cmd.getOptionValues("l");
		
		// Check to see if the argument was set
		if (args != null) {
			StringBuffer license = new StringBuffer();
			boolean first = true;

			for (String s : args) {
				// Add a space on successive arguments
				license.append(first ? s : " " + s);
				first = false;
			}
			LOG.debug("license: " + license.toString());
			setLicense(license.toString());
		}
		else {
			// If the property boundary.tools.snmp.license is set use this value
			// This is typically set from the environment in the script wrapping
			// the execution of the java program
			String license = System.getProperty(LICENSE);
			if (license != null) {
				setLicense(license);
			}
		}
	}
	
	/**
	 * Sets the path to the MIBs passed on the command line.
	 * 
	 * @return
	 */
	private boolean setInputMIBs() {
		boolean ok = false;
		String path = cmd.getOptionValue("m");

		if (path != null) {

			File f = new File(path);
			if (f != null && f.canRead()) {
				mibPath = f.getAbsolutePath();
				ok = true;
			}
			else {
				LOG.error("Unable to read MIBs from " + path);
			}
		}

		return ok;
	}
	
	public boolean setOutputDirectory() {
		boolean ok = false;

		if (cmd.hasOption("r")) {
			mibTargetDir = cmd.getOptionValue("r");
			setRepository(mibTargetDir);
			ok = true;
		}
		return ok;
	}

	/**
	 * Execute the compilation of the MIBs.
	 * 
	 * @param args Arguments passed on the command line
	 * @throws IOException thrown by MIB compilation
	 */
	public void execute(String[] args) throws IOException {

		// Handle the command line arguments
		parseCommandLineOptions(args);
		
		// Check to see
		DEBUG = Boolean.getBoolean(cmd.getOptionValue("d"));
		
		setUpdateExisting(cmd.hasOption("u"));

		// Set the strictness of the MIB compilation based on the
		// command line arguments
		setCompilerStrictness();

		// Set the SNMP4J compiler license if provided, required for
		// MIBs using the enterprise branch
		getCompilerLicense();
		

		// If we have our MIBs to compile and store the
		// results the proceed with the compilation
		if (setInputMIBs() && setOutputDirectory()) {
			
			// Write debug information before compilation
			LOG.info("Input MIB file(s)/directory: " + mibPath);
			LOG.info("Output MIB directory: " + mibTargetDir);
			LOG.info("Compile Leniently: " + isCompileLeniently());

			// Initialize the SMI Manager
			initialize();
			
			// Compile the MIBs
			compile(mibPath);
		}
		else {
			usage();
		}
	}

	/**
	 * @param args command line arguments
	 * @throws IOException exception occurs during MIB compilation
	 */

	public static void main(String[] args) throws IOException {
		MIBCompiler mibCompiler = new MIBCompiler();
		mibCompiler.execute(args);
	}
}
