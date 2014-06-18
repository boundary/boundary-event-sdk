package com.boundary.sdk.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.syslog.SyslogMessageGenerator;

public class EventCLI {
	
	private static Logger LOG = LoggerFactory.getLogger(EventCLI.class);
	
	public static final char OPTION_HELP = 'h';
	public static final char OPTION_API_KEY = 'a';
	public static final char OPTION_API_HOST = 'b';
	public static final char OPTION_ORG_ID = 'o';
	public static final char OPTION_CREATED_AT = 'z';
	public static final char OPTION_FINGERPRINT_FIELDS = 'f';
	public static final char OPTION_MESSAGE = 'm';
	public static final char OPTION_PROPERTIES = 'p';
	public static final char OPTION_RECEIVED_AT = 'r';
	public static final char OPTION_SENDER = 'x';
	public static final char OPTION_SEVERITY = 'y';
	public static final char OPTION_SOURCE = 'u';
	public static final char OPTION_STATUS = 'w';
	public static final char OPTION_TAGS = 't';
	public static final char OPTION_TITLE = 'n';

	private Options options;

	private Option optionHelp;
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
	
	private CamelContext context;
	private BoundaryEventRouteBuilder eventRouteBuilder;

	public EventCLI() {
		// create Options object
		options = new Options();
		event = new RawEvent();
	}
	
	private void usage() {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String cliName = System.getProperty("boundary.event.cliName","bevent");
		formatter.printHelp(cliName, options);
	}
	
	@SuppressWarnings("static-access")
	private void addApiHostOption() {
		optionApiHost = OptionBuilder
				.withArgName("api_host)")
				.hasArg()
				.withDescription("Boundary API Host. defaults to api.boundary.com")
				.create(OPTION_API_HOST);
		options.addOption(optionApiHost);
	}
	
	@SuppressWarnings("static-access")
	private void addCommandOption() {
		optionCommand = OptionBuilder
				.withArgName("command")
				.hasArg()
				.withDescription("One of CREATE, UPDATE, OR DELETE. Default is CREATE.")
				.create("c");
		options.addOption(optionCommand);
	}
	
	@SuppressWarnings("static-access")
	private void addApiKeyOption() {
		optionApiKey = OptionBuilder
				.withArgName("api_key")
				.hasArg()
				.withDescription("Boundary API Key")
				.withLongOpt("api-key").create(OPTION_API_KEY);
		options.addOption(optionApiKey);
	}
	
	@SuppressWarnings("static-access")
	private void addOrganizationIdOption() {
		optionOrganizationId = OptionBuilder
				.withArgName("org_id")
				.hasArg()
				.withDescription("Boundary organization Id")
				.withLongOpt("org-id")
				.create(OPTION_ORG_ID);
		options.addOption(optionOrganizationId);
	}
	
	@SuppressWarnings("static-access")
	private void addCreatedAtOption() {
		optionCreatedAt = OptionBuilder
				.withArgName("yyyy-mm-dd HH-MM-SS")
				.hasArg()
				.withDescription("Date and time of event creation (UTC)")
				.withLongOpt("created-at")
				.create(OPTION_CREATED_AT);
		options.addOption(optionCreatedAt);
	}
	
	@SuppressWarnings("static-access")
	private void addFingerprintFieldsOption() {
		optionFingerprintFields = OptionBuilder
				.withArgName("field-name")
				.hasArg()
				.hasArgs(16)
                .withValueSeparator(':')
                .isRequired()
				.withDescription("The fields of the event used to calculate the event fingerprint.")
				.withLongOpt("fingerprint-fields")
				.create(OPTION_FINGERPRINT_FIELDS);
		options.addOption(optionFingerprintFields);
	}
	
	@SuppressWarnings("static-access")
	private void addMessageOption() {
		optionMessage = OptionBuilder
				.withArgName("message")
				.hasArg()
				.withDescription("Additional description of the event")
				.withLongOpt("message")
				.create(OPTION_MESSAGE);
		options.addOption(optionMessage);
	}
	
	@SuppressWarnings("static-access")
	private void addPropertiesOption() {
		optionProperties = OptionBuilder
				.withArgName("key:value[,key:value]")
				.hasArg()
				.withDescription("Properties for the event.")
				.hasArgs(2)
                .withValueSeparator()
				.withLongOpt("properties").create(OPTION_PROPERTIES);
		options.addOption(optionProperties);
	}
	
	@SuppressWarnings("static-access")
	private void addReceivedAtOption() {
		optionReceivedAt = OptionBuilder.withArgName("yyyy-mm-dd HH-MM-SS").hasArg()
				.withDescription("The timestamp the event was received (UTC).")
				.withLongOpt("received-at").create(OPTION_RECEIVED_AT);
		options.addOption(optionReceivedAt);
	}
	
	@SuppressWarnings("static-access")
	private void addSenderOption() {
		optionSender = OptionBuilder
				.withArgName("ref[:type:name]")
				.hasArgs(3)
                .withValueSeparator(':')
				.withDescription("Optional information about the sender of the event.")
				.withLongOpt("sender")
				.create(OPTION_SENDER);
		options.addOption(optionSender);
	}
	
	@SuppressWarnings("static-access")
	private void addSeverityOption() {
		optionSeverity = OptionBuilder
				.withArgName("INFO|WARN|ERROR|CRITICAL")
				.hasArg()
				.withDescription("Severity of the event which is one of INFO, WARN, ERROR, CRITICAL. Default is INFO.")
				.withLongOpt("severity")
				.create(OPTION_SEVERITY);
		options.addOption(optionSeverity);
	}
	
	@SuppressWarnings("static-access")
	private void addSourceOption() {
		optionSource = OptionBuilder
				.withArgName("ref:[type:name]")
				.hasArgs(3)
				.withValueSeparator(':')
				.withDescription("The source of the event. The source is typically the hostname or ip address of the system this event refers to. If type is not provided it is assumed to be 'host'")
				.withLongOpt("source")
				.create(OPTION_SOURCE);
		options.addOption(optionSource);
	}
	
	@SuppressWarnings("static-access")
	private void addStatusOption() {
		optionStatus = OptionBuilder
				.withArgName("OPEN|CLOSED|ACKNOWLEDGED|OK")
				.hasArg()
				.withDescription("One of OPEN, CLOSED, ACKNOWLEDGED, or OK. Default is OK")
				.withLongOpt("status")
				.create(OPTION_STATUS);
		options.addOption(optionStatus);
	}
	
	@SuppressWarnings("static-access")
	private void addTagOption() {
		optionTags = OptionBuilder
				.withArgName("tag1[:tag2][:tag3][:...]")
				.hasArg()
				.withDescription("Tags used to provide a classification for events.")
				.withLongOpt("tags").create(OPTION_TAGS);
		options.addOption(optionTags);
	}
	
	@SuppressWarnings("static-access")
	private void addTitleOption() {
		optionTitle = OptionBuilder
				.withArgName("title")
				.hasArg()
				.isRequired()
				.withDescription("Title of the event")
				.withLongOpt("title").create(OPTION_TITLE);
		options.addOption(optionTitle);
	}
	
	@SuppressWarnings("static-access")
	private void addHelpOption() {
		optionHelp = OptionBuilder
				.withDescription("Shows help")
				.withLongOpt("help").create(OPTION_HELP);
		options.addOption(optionHelp);
	}

	/**
	 * Configures all of the command line options handled by the Event CLI
	 */
	private void configureCommandLineOptions() {

		addApiHostOption();
		addCommandOption();
		addApiKeyOption();
		addOrganizationIdOption();
		addCreatedAtOption();
		addFingerprintFieldsOption();
		addMessageOption();
		addPropertiesOption();
		addReceivedAtOption();
		addSenderOption();
		addSeverityOption();
		addSourceOption();
		addStatusOption();
		addTagOption();
		addTitleOption();
		addHelpOption();
	}
	protected boolean handleCommandlandArguments(String[] args) throws ParseException {
		boolean exit = false;
		
		configureCommandLineOptions();
		
		CommandLineParser parser = new BasicParser();
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption(OPTION_HELP)) {
				usage();
				exit = true;
			}
		} catch (ParseException e) {
			usage();
//			e.printStackTrace();
			exit = true;
		}
		return exit;
	}
	
	protected RawEvent getEvent() {
		return this.event;
	}
	

	protected Date parseDateTime(String s) throws java.text.ParseException {
		Date dt = null;
		if (s != null) {
			Calendar c = DatatypeConverter.parseDateTime(s);
			c.setTimeZone(TimeZone.getTimeZone("GMT"));
			dt = c.getTime();
		}
		return dt;
	}
	
	protected void extractCreatedAt() throws java.text.ParseException {
		LOG.debug("extract createdAt");
		String createdAt = optionCreatedAt.getValue();
		event.setCreatedAt(parseDateTime(createdAt));
	}
	
	protected void extractReceivedAt() throws java.text.ParseException {
		String receivedAt = optionReceivedAt.getValue();
		event.setReceivedAt(parseDateTime(receivedAt));
	}
	
	protected void extractFingerprintFields() {
		LOG.debug("extract fingerprintFields");
		// Extract the fingerprint fields
		String[] fingerprintFields = cmd.getOptionValues(OPTION_FINGERPRINT_FIELDS);
		if (fingerprintFields != null) {
			for (String s : fingerprintFields) {
				event.addFingerprintField(s);
			}
		}
	}
	
	protected void extractOrganizationId() {
		String orgId = cmd.getOptionValue(OPTION_ORG_ID);
		if (orgId != null) {
			event.setOrganizationId(orgId);
		}
	}
	
	protected void extractMessage() {
		String message = cmd.getOptionValue('m');
		if (message != null) {
			event.setMessage(message);
		}
	}
	
	protected void extractProperties() {
		String [] properties = cmd.getOptionValues('p');
		if (properties != null) {
			event.addProperty("","");
		}
	}
	
	protected void extractSeverity() {
		String severity = cmd.getOptionValue('s');
	}
	
	protected void extractStatus() {
		
	}
	
	protected void extractTags() {
		String [] tags = cmd.getOptionValues('t');
	}
	
	/**
	 * Extracts the fields for the Source type.
	 * @param strs Array of strings from the command line
	 * @return {@link Source}
	 */
	private Source getSourceType(String [] strs) {
		Source s = null;
		LOG.debug("strs[0] = " + (strs != null && strs.length >= 1 ? strs[0] : ""));
		LOG.debug("strs[1] = " + (strs != null && strs.length >= 2 ? strs[1] : ""));
		LOG.debug("strs[2] = " + (strs != null && strs.length >= 2 ? strs[2] : ""));

		// If have the string tokens and the cardinality is correct then
		// extract the values and assign to the Source types
		if (strs != null && strs.length >= 1 && strs.length <= 3) {
			s = new Source();
			if (strs.length >= 1) {
				s.setRef(strs[0]);
			}
			if (strs.length >= 2) {
				s.setType(strs[1]);
			} else {
				s.setType("host");
			}
			// Source name is in the last array value if present
			if (strs.length == 3) {
				s.setName(strs[2]);
			}
		}
		return s;
	}
	
	/**
	 * Extract the source fields from the command line options
	 */
	protected void extractSource() {
		Source source = getSourceType(cmd.getOptionValues('u'));
		LOG.debug("source = " + source);
		// @TODO support for properties for the source
		if (source != null) {
			event.setSource(source);
		}
	}
	
	/**
	 * Extract the sender fields from the command line option
	 */
	protected void extractSender() {		
		Source sender = getSourceType(cmd.getOptionValues('u'));
		// @TODO support for properties for the source
		if (sender != null) {
			event.setSender(sender);
		}
	}
	
	/**
	 * Extracts the title from the comman line option.
	 */
	protected void extractTitle() {
		String title = cmd.getOptionValue("n");
		if (title != null) {
			event.setTitle(title);
		}
	}
	
	/**
	 * Configures the event from the command line options.
	 * 
	 * @throws java.text.ParseException If there is an issue parsing the command line options
	 */
	protected void configureEvent() throws java.text.ParseException {
		extractCreatedAt();
		extractFingerprintFields();
		extractOrganizationId();
		extractMessage();
		extractReceivedAt();
		extractSender();
		extractSeverity();
		extractStatus();
		extractTags();
		extractSource();
		extractProperties();
		extractTitle();
	}
	
	public void setDebugEvent() {
		event.addFingerprintField("@title");
		event.setTitle("Hello from the CLI");
		event.getSource().setRef("localhost");
		event.getSource().setType("host");
	}
	
	protected void configureApiKey() {
		String apiKey = cmd.getOptionValue('a');
		eventRouteBuilder.setApiKey(apiKey);
	}
	
	protected void configureOrgId() {
		String orgId = cmd.getOptionValue('o');
		if (orgId != null) {
			eventRouteBuilder.setOrgId(orgId);
		}
	}
	
	protected void configureApiHost() {
		eventRouteBuilder.setApiHost("api.boundary.com");
	}

	public void addEventRoute(CamelContext context) throws Exception {
		eventRouteBuilder = new BoundaryEventRouteBuilder();
		eventRouteBuilder.setRouteId("EVENT-ROUTE");
		eventRouteBuilder.setFromUri("direct:event");
		eventRouteBuilder.setStartUpOrder(100);
		configureApiKey();
		configureOrgId();
		context.addRoutes(eventRouteBuilder);
	}
	
	// TODO: Elminate the need for this extra route
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
	
	/**
	 * Sends an event to a route that hits the Boundary API.
	 * 
	 */
	public void sendEvent() {
		try {
			// Create a producer template so that we can send an instance of RawEvent
			// which ends up at the boundary API
			ProducerTemplate template = context.createProducerTemplate();
			
			// This blocks until the event is sent
			template.sendBody("direct:source",event);
			
			// Stop the context and the routes associated with it.
			context.stop();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	private void configureRoutes() throws Exception {
		// Allocate a CamelContext that will contain our Routes
		context = new DefaultCamelContext();

		// Add the event route and route that serializes the RawEvent
		addEventRoute(context);
		addSourceRoute(context);

		// Start the context which will in turn start the defined within the
		// context.
		context.start();
	}
	
	/**
	 * Handles the processing of command line options, configuring, and sending the events
	 * @param args
	 * @return
	 * @throws Exception
	 */
	private String execute(String [] args) throws Exception {
		// If there were no errors parsing the command line
		// then proceed with configuring and sending the event
		if (configure(args) == false) {
			configureRoutes();
			sendEvent();
		}
		// @TODO: Return the event id created
		return null;
	}
	
	protected boolean configure(String [] args) throws Exception {
		boolean result = handleCommandlandArguments(args);
		if (result == false) {
			configureEvent();
		}
		return result;
	}

	/**
	 * Main entry point of the Event CLI.
	 * 
	 * @param args Command line arguments
	 */
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
