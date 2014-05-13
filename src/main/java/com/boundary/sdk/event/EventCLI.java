package com.boundary.sdk.event;

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
	
	private Option optionCreatedAt;
	private Option optionFingerprintFields;
	private Option optionMessage;
//	private Option optionOrganizationId;
	private Option optionProperties;
	private Option optionReceivedAt;
	private Option optionSender;
	private Option optionSeverity;
	private Option optionSource;
	private Option optionStatus;
	private Option optionTags;
	private Option optionTitle;

	public EventCLI() {
		// create Options object
		options = new Options();
	}

	@SuppressWarnings("static-access")
	private void handleCommandlandArguments(String[] args) {
		apiHost = OptionBuilder.withArgName("api_host").hasArg()
				.withDescription("Boundary API Host. defaults to api.boundary.com").create("b");
		command = OptionBuilder.withArgName("command").hasArg()
				.withDescription("One of CREATE, UPDATE, OR DELETE").create("c");
		apiKey = OptionBuilder.withArgName("api_key").hasArg()
				.withDescription("Boundary API Key").create("a");
		apiKey.setLongOpt("api-key");
		orgId = OptionBuilder.withArgName("org_id").hasArg()
				.withDescription("Boundary organization Id")
				.withLongOpt("org-id").create("o");
		
		optionCreatedAt = OptionBuilder.withArgName("yyyy-mm-dd HH-MM-SS").hasArg()
				.withDescription("Date and time of event creation")
				.withLongOpt("created-at").create("z");
		
		optionFingerprintFields = OptionBuilder.withArgName("field-name").hasArg()
				.withDescription("The fields of the event used to calculate the event fingerprint.")
				.withLongOpt("fingerprint-fields").create("f");
		
		optionMessage = OptionBuilder.withArgName("message").hasArg()
				.withDescription("Additional description of the event")
				.withLongOpt("message").create("m");
		
		optionProperties = OptionBuilder.withArgName("properties").hasArg()
				.withDescription("Properties for the event.")
				.withLongOpt("properties").create("p");
		
		optionReceivedAt = OptionBuilder.withArgName("yyyy-mm-dd HH-MM-SS").hasArg()
				.withDescription("The timestamp the event was received.")
				.withLongOpt("received-at").create("r");
		
		optionSender = OptionBuilder.withArgName("ref:type[:name]").hasArg()
				.withDescription("Optional information about the sender of the event.")
				.withLongOpt("sender").create("x");
		
		optionSeverity = OptionBuilder.withArgName("severity").hasArg()
				.withDescription("Severity of the event which is one of INFO, WARN, ERROR, CRITICAL. Default is INFO.")
				.withLongOpt("severity").create("y");

		optionSource = OptionBuilder.withArgName("ref:type[:name]").hasArg()
				.withDescription("The source of the event. The source is typically the hostname or ip address of the system this event refers to.")
				.withLongOpt("status").create("u");
		
		optionStatus = OptionBuilder.withArgName("status").hasArg()
				.withDescription("One of OPEN, CLOSED, ACKNOWLEDGED, or OK.")
				.withLongOpt("status").create("w");
		
		optionTags = OptionBuilder.withArgName("tag1[:tag2][:tag3][:...]").hasArg()
				.withDescription("Tags used to provide a classification for events.")
				.withLongOpt("tags").create("t");
		
		optionTitle = OptionBuilder
					.withDescription("Title of the event")
					.withLongOpt("title").create("n");

		try {
			// add h option
			options.addOption("h", false, "Show help");

			// add v option
			options.addOption(apiHost);
			options.addOption(apiKey);
			options.addOption(orgId);
			options.addOption(command);
			
			// Event options
			options.addOption(optionCreatedAt);
			options.addOption(optionFingerprintFields);
			options.addOption(optionMessage);
//			options.addOption(optionOrganizationId);
			options.addOption(optionProperties);
			options.addOption(optionReceivedAt);
			options.addOption(optionSender);
			options.addOption(optionSeverity);
			options.addOption(optionSource);
			options.addOption(optionStatus);
			options.addOption(optionTitle);
			options.addOption(optionTags);

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
