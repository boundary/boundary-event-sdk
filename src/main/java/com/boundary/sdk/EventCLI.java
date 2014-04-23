package com.boundary.sdk;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

public class EventCLI {

	private Options options;
	private Option apiHost;
	private Option apiKey;
	private Option command;
	private Option orgId;

	public EventCLI() {
		// create Options object
		options = new Options();
	}

	@SuppressWarnings("static-access")
	private void handleCommandlandArguments(String[] args) {
		apiHost = OptionBuilder.withArgName("api_host").hasArg().create("b");
		command = OptionBuilder.withArgName("command").hasArg().create("c");
		apiKey = OptionBuilder.withArgName("api_key").hasArg().create("a");
		apiKey.setLongOpt("api-key");
		orgId = OptionBuilder.withArgName("org_id").hasArg()
				.withDescription("Boundary organization Id")
				.withLongOpt("org-id").create("o");

		try {

			// add t option
			options.addOption("t", false, "display current time");
			// add c option
			options.addOption("c", true, "country code");
			// add h option
			options.addOption("h", false, "show help");

			// add v option
			options.addOption(apiHost);
			options.addOption(apiKey);
			options.addOption(orgId);
			options.addOption(command);

			CommandLineParser parser = new BasicParser();
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("h")) {
				// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("becli", options);
				System.exit(0);
			}

		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		EventCLI event = new EventCLI();

		event.handleCommandlandArguments(args);

	}

}
