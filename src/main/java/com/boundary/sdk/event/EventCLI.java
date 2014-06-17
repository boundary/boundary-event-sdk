package com.boundary.sdk.event;

import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
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

	private Option optionApiKey;
	private Option optionApiHost;
	private Option optionCommand;
	private Option optionCreatedAt;
	private Option optionFingerprintFields;
	private Option optionMessage;
	private Option optionOrganizationId;
	private Option optionProperties;
	private Option optionReceivedAt;
	private Option optionSender;
	private Option optionSeverity;
	private Option optionSource;
	private Option optionStatus;
	private Option optionTags;
	private Option optionTitle;
	
	private RawEvent event;
	private CommandLine cmd;

	public EventCLI() {
		// create Options object
		options = new Options();
		event = new RawEvent();
	}

	@SuppressWarnings("static-access")
	protected boolean handleCommandlandArguments(String[] args) throws ParseException {
		boolean exit = false;
		
		optionApiHost = OptionBuilder.withArgName("api_host").hasArg()
				.withDescription("Boundary API Host. defaults to api.boundary.com").create("b");
		optionCommand = OptionBuilder.withArgName("command").hasArg()
				.withDescription("One of CREATE, UPDATE, OR DELETE. Default is CREATE.").create("c");
		optionApiKey = OptionBuilder.withArgName("api_key").hasArg()
				.withDescription("Boundary API Key").create("a");
		optionApiKey.setLongOpt("api-key");
		optionOrganizationId = OptionBuilder.withArgName("org_id").hasArg()
				.withDescription("Boundary organization Id")
				.withLongOpt("org-id").create("o");
		
		optionCreatedAt = OptionBuilder.withArgName("yyyy-mm-dd HH-MM-SS").hasArg()
				.withDescription("Date and time of event creation (UTC)")
				.withLongOpt("created-at").create("z");
		
		optionFingerprintFields = OptionBuilder.withArgName("field-name").hasArg()
				.hasArgs(16)
                .withValueSeparator(':')
				.withDescription("The fields of the event used to calculate the event fingerprint.")
				.withLongOpt("fingerprint-fields").create("f");
		
		optionMessage = OptionBuilder.withArgName("message").hasArg()
				.withDescription("Additional description of the event")
				.withLongOpt("message").create("m");
		
		optionProperties = OptionBuilder.withArgName("key:value[,key:value]").hasArg()
				.withDescription("Properties for the event.")
				.hasArgs(2)
                .withValueSeparator()
				.withLongOpt("properties").create("p");
		
		optionReceivedAt = OptionBuilder.withArgName("yyyy-mm-dd HH-MM-SS").hasArg()
				.withDescription("The timestamp the event was received (UTC).")
				.withLongOpt("received-at").create("r");
		
		optionSender = OptionBuilder.withArgName("ref[:type:name]")
				.hasArgs(3)
                .withValueSeparator(':')
				.withDescription("Optional information about the sender of the event.")
				.withLongOpt("sender").create("x");
		
		optionSeverity = OptionBuilder.withArgName("INFO|WARN|ERROR|CRITICAL")
				.hasArg()
				.withDescription("Severity of the event which is one of INFO, WARN, ERROR, CRITICAL. Default is INFO.")
				.withLongOpt("severity").create("y");

		optionSource = OptionBuilder.withArgName("ref:[type:name]")
				.hasArgs(3)
				.withValueSeparator(':')
				.withDescription("The source of the event. The source is typically the hostname or ip address of the system this event refers to.  If type is not provided it is assumed to be 'host'")
				.withLongOpt("source").create("u");
		
		optionStatus = OptionBuilder.withArgName("OPEN|CLOSED|ACKNOWLEDGED|OK")
				.hasArg()
				.withDescription("One of OPEN, CLOSED, ACKNOWLEDGED, or OK. Default is OK")
				.withLongOpt("status").create("w");
		
		optionTags = OptionBuilder.withArgName("tag1[:tag2][:tag3][:...]").hasArg()
				.withDescription("Tags used to provide a classification for events.")
				.withLongOpt("tags").create("t");
		
		optionTitle = OptionBuilder.withArgName("title").hasArg()
					.withDescription("Title of the event")
					.withLongOpt("title").create("n");

		// add h option
		options.addOption("h", false, "Show help");

		// Event options
		options.addOption(optionApiHost);
		options.addOption(optionApiKey);
		options.addOption(optionCommand);
		options.addOption(optionCreatedAt);
		options.addOption(optionFingerprintFields);
		options.addOption(optionMessage);
		options.addOption(optionOrganizationId);
		options.addOption(optionProperties);
		options.addOption(optionReceivedAt);
		options.addOption(optionSender);
		options.addOption(optionSeverity);
		options.addOption(optionSource);
		options.addOption(optionStatus);
		options.addOption(optionTitle);
		options.addOption(optionTags);

		CommandLineParser parser = new BasicParser();
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("h")) {
				// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("becli", options);
				exit = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return exit;
	}
	
	protected RawEvent getEvent() {
		return this.event;
	}
	
	
	protected void populateEvent() {
		
//		String createdAt = optionCreatedAt.getValue();
//		if (createdAt.length() != 0) {
//			event.setCreatedAt(new Date(createdAt));
//		}
		
//		private Option optionFingerprintFields;
		// Extract the fingerprint fields
		String[] fingerprintFields = cmd.getOptionValues("f");
		if (fingerprintFields != null) {
			for (String s : fingerprintFields) {
				event.addFingerprintField(s);
			}
		}
		
//		private Option optionMessage;
		String message = cmd.getOptionValue('m');
		if (message != null) {
			event.setMessage(message);
		}
//		private Option optionOrganizationId;
//		private Option optionProperties;
//		private Option optionReceivedAt;
//		private Option optionSender;
//		private Option optionSeverity;
//		private Option optionSource;
		
		// Set the source
		// @TODO support for properties for the source
		String[] source = cmd.getOptionValues("u");
		if (source != null) {
			Source s = event.getSource();
			if (source.length >= 1) {
				s.setRef(source[0]);
			}
			if (source.length >= 2) {
				s.setType(source[1]);
			}
			
			if (source.length == 3) {
				s.setName(source[2]);
			}
		}

//		private Option optionStatus;
//		private Option optionTags;
		
		String tags = cmd.getOptionValue("t");
//		private Option optionTitle;
		
		String properties = cmd.getOptionValue("p");
		if (properties != null) {
			event.addProperty("","");
		}
		String orgId = cmd.getOptionValue("o");
		if (orgId != null) {
			event.setOrganizationId(orgId);
		}
		
		String title = cmd.getOptionValue("n","@message");
		event.setTitle(title);
	}
	
	public void setDebugEvent(RawEvent event) {
		
	}
	
	public void addEventRoute(CamelContext context) throws Exception {

		BoundaryEventRouteBuilder eventRouteBuilder = new BoundaryEventRouteBuilder();
		eventRouteBuilder.setApiHost("api.boundary.com");
		eventRouteBuilder.setFromUri("direct:event");
		eventRouteBuilder.setApiKey("Aqixh4Vt6j10d75Yg9zPrKvDLWc");
		eventRouteBuilder.setOrgId("3ehRi7uZeeaTN12dErF5XOnRXjC");
		eventRouteBuilder.setStartUpOrder(100);
		eventRouteBuilder.setRouteId("EVENT-ROUTE");
		
		context.addRoutes(eventRouteBuilder);
	}
	
	private class EventSourceRouteBuilder extends BoundaryRouteBuilder {

		@Override
		public void configure() throws Exception {
			from("direct:source")
			.id("SOURCE-ROUTE")
			.log("SENDING EVENT")
			.startupOrder(200)
			.marshal().serialization()
			.to("direct:event");
		}
	}
	public void addSourceRoute(CamelContext context) throws Exception {
		EventSourceRouteBuilder source = new EventSourceRouteBuilder();
		
		context.addRoutes(source);
	}
	

	
	public void createEvent() {

		try {
			CamelContext context = new DefaultCamelContext();
			addEventRoute(context);
			addSourceRoute(context);
			context.start();

			ProducerTemplate template = context.createProducerTemplate();

			event.addFingerprintField("@title");
			event.setTitle("Hello from the CLI");
			event.getSource().setRef("localhost");
			event.getSource().setType("host");

			template.sendBody("direct:source", event);
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	private String execute(String [] args) throws Exception {
		
		handleCommandlandArguments(args);
		populateEvent();
		createEvent();

		return null;
	}

	public static void main(String[] args) {
		EventCLI cli = new EventCLI();

		try {
			String eventId = cli.execute(args);
			
			if (eventId != null) {
				System.out.println(eventId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
