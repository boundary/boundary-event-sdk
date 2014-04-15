package com.boundary.sdk;

import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

public class EventCLI {

	public EventCLI() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// create Options object
		Options options = new Options();

		Option apiHost = OptionBuilder.withArgName("api_host").hasArg().create("b");
		Option command = OptionBuilder.withArgName("command").hasArg().create("c");
		Option apiKey = OptionBuilder.withArgName("api_key").hasArg().create("a");
		apiKey.setLongOpt("api-key");
		Option orgId = OptionBuilder.withArgName("org_id")
				.hasArg()
				.withDescription("Boundary organization Id")
				.withLongOpt("org-id")
				.create("o");


		

		try {

			// add t option
			options.addOption("t", false, "display current time");
			// add c option
			options.addOption("c", true, "country code");
			// add h option
			options.addOption("h",false,"show help");
			
			// add v option
			options.addOption(apiHost);
			options.addOption(apiKey);
			options.addOption(orgId);
			options.addOption(command);
			

			CommandLineParser parser = new BasicParser();
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("t")) {
				//System.out.println("true");
			} else {
				//System.out.println("false");
			}
			
			if (cmd.hasOption("h")) {
			
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("becli", options );
			System.exit(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
