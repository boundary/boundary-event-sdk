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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExportMIB extends SmiSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(ExportMIB.class);

	private static final String COMMAND_NAME="ExportMIB.command.name";
	private String commandName;
	
	public static final String LICENSE="boundary.tools.snmp.license";
	private static String license;


	private CommandLine cmd;
	private Options options;
	private Option exportOption;
	private Option helpOption;
	private Option licenseOption;
	private Option repoDirOption;
	private Option silentOption;
	private Option mibOption;
	
	private enum ExportType {METRICS,OIDS};
	private ExportType exportType;
	
	private List<String> mibList;
	
	private boolean silent;


	public ExportMIB() {
		options = new Options();
		mibList = new ArrayList<String>();
		commandName = System.getProperty(COMMAND_NAME,this.getClass().getSimpleName());
		exportType = ExportType.METRICS;
		license = null;
		silent = false;
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

	
	@SuppressWarnings("static-access")
	private void buildOptions() {
		helpOption = OptionBuilder
				.withDescription("Display help information")
				.withLongOpt("help")
				.create("?");
		
		licenseOption = OptionBuilder.withArgName("license")
				.hasArgs()
				.withValueSeparator(' ')
				.withDescription("The license key string you received with the purchase a SNMP4J-SMI license or its evaluation."
						+ "You may provide leave empty, but then you may not use any MIB modules of the \"enterprise\" OID subtree.")
				.withLongOpt("license")
				.create("l");

		repoDirOption = OptionBuilder.withArgName("repository_dir")
				.hasArg()
				.isRequired()
				.withDescription("Directory where the "+ commandName + " can read compiled MIB modules.")
				.withLongOpt("repository-dir")
				.create("r");
		
		silentOption = OptionBuilder
				.withDescription("Quiet mode, suppress normal output.")
				.withLongOpt("quiet")
				.create("q");
		
		exportOption = OptionBuilder
				.withArgName("type")
				.hasArgs(1)
				.withDescription("Selects what to export which is either: oids, or metric. Defaults to metrics.")
				.withLongOpt("export")
				.create("e");
		
		mibOption = OptionBuilder.withArgName("mib_name")
				.hasArgs()
				.isRequired()
				.withValueSeparator(',')
				.withDescription("List of MIB(s) to export. Example: SNMPv2-MIB")
				.withLongOpt("mib")
				.create("f");

		options.addOption(helpOption);
		options.addOption(licenseOption);
		options.addOption(silentOption);
		options.addOption(repoDirOption);
		options.addOption(exportOption);
		options.addOption(mibOption);

	}
	
	private void getMIBs() {
		String[] mibs = cmd.getOptionValues("f");

		for (String mib : mibs) {
			mibList.add(mib);
		}
		if (silent == false) {
			System.out.printf("MIBs to export: %s%n",mibList);
		}
	}
	
	public void setOutputDirectory() {
		if (cmd.hasOption("r")) {
			String mibTargetDir = cmd.getOptionValue("r");
			setRepository(mibTargetDir);
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

	private void parseCommandLineOptions(String[] args) throws Exception {

		buildOptions();
		
		CommandLineParser parser = new BasicParser();
		cmd = parser.parse(options, args);

		// If the help argument is present then display usage
		if (cmd.hasOption("?") == true) {
			usage();
		}
		
		silent = cmd.hasOption("q");
		
		getMIBs();
		setOutputDirectory();
		// Set the SNMP4J compiler license if provided, required for
		// MIBs using the enterprise branch
		getCompilerLicense();
	}

	
	private void export() {
		
		initialize();
		loadModules();
	}

	private void execute(String [] args) {
		try {
			this.parseCommandLineOptions(args);
			this.export();
		} catch (ParseException e) {
			this.usage();
		} catch (Exception e) {
			System.err.printf("%s%n",e);
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		ExportMIB mib2metrics = new ExportMIB();
		mib2metrics.execute(args);
	}

}
