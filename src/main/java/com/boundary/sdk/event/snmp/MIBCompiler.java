/**
 * 
 */
package com.boundary.sdk.event.snmp;

//import com.snmp4j.smi.*;

import java.io.File;
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
 * Commandline tool to compile MIBs
 * 
 * @author davidg
 * 
 */
public class MIBCompiler extends SmiSupport {

	private static Logger LOG = LoggerFactory.getLogger(MIBCompiler.class);
	
	private static final String BOUNDARY_TOOLS_COMMAND_NAME_PROP_NAME="boundary.tools.command.name";
	
	private Options options = new Options();
	private Option repoDirOption;
	private Option mibDirOption;
	private Option licenseOption;
	private Option helpOption;
	
	// Used to store the name of the wrapper script that is called
	String commandName; 

	/**
	 * Default constructor
	 */
	public MIBCompiler() {
		options = new Options();
		commandName = this.getClass().toString();
		commandName = System.getProperty(BOUNDARY_TOOLS_COMMAND_NAME_PROP_NAME);
		if (commandName == null) {
			commandName = this.getClass().toString();
		}
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
	 * @param args
	 */
	@SuppressWarnings("static-access")
	public void handleCommandLineOptions(String[] args) {
		
		for (String s :args) {
			LOG.debug(s);
		}

		repoDirOption = OptionBuilder.withArgName("repository_dir")
				.hasArg()
				.isRequired()
				.withDescription("An empty directory where the "
				+ commandName + "can read and store compiled MIB modules persistently. "  + 
						"Directory must not contain any plain text (i.e., uncompiled) MIB files.")
				.withLongOpt("repository-dir")
				.create("r");
		mibDirOption = OptionBuilder.withArgName("mib_dir")
				.hasArg()
				.isRequired()
				.withDescription("A single MIB or a directory of MIBs to compile.")
				.withLongOpt("mib-directory")
				.create("m");
		helpOption = OptionBuilder
				.withDescription("Display help information")
				.withLongOpt("help")
				.create("h");
		licenseOption = OptionBuilder.withArgName("license")
				.hasOptionalArg()
				.withValueSeparator(' ')
				.withDescription("The license key string you received with the purchase a SNMP4J-SMI license or its evaluation."
						+ "You may provide leave empty, but then you may not use any MIB modules of the \"enterprise\" OID subtree.")
				.withLongOpt("license")
				.create("l");

		
		options.addOption(repoDirOption);
		options.addOption(mibDirOption);
		options.addOption(licenseOption);
		options.addOption(helpOption);

		try {
			CommandLineParser parser = new BasicParser();
			CommandLine cmd = parser.parse(options, args);
			
			//System.out.println(licenseOption.getValues());

			if (cmd.hasOption("h")) {
				usage();
			}

		} catch (Exception e) {
			usage();
		}
	}

	/**
	 * Execute the compilation of the MIBs.
	 * 
	 * @param args
	 */
	public void execute(String[] args) {

		// Handle the command line arguments
		handleCommandLineOptions(args);
		
		setLicense(licenseOption.getValue());
		System.out.println(mibDirOption.getValue());
		File mibDirectory = new File(this.mibDirOption.getValue());
		LOG.info(mibDirectory.getAbsolutePath());
		setRepository(mibDirectory.getAbsolutePath());
		initialize();
		compile(new File(this.mibDirOption.getValue()));
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */

	public static void main(String[] args) {
		MIBCompiler mibCompiler = new MIBCompiler();
		mibCompiler.execute(args);
	}
}
