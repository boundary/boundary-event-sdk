package com.boundary.sdk.event.syslog;


import java.util.Date;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.snmp.SmiSupport;

/**
 * Class to generate syslog messages for testing
 * 
 * @author davidg
 *
 */
public class SyslogMessageGenerator {
	
	private static Logger LOG = LoggerFactory.getLogger(SyslogMessageGenerator.class);
	
	private SyslogIF syslog;
	
	private static final int DEFAULT_COUNT=10;
	private static final int DEFAULT_DELAY=10;
	
	private int count = DEFAULT_COUNT;
	private int delay = DEFAULT_DELAY;

	public SyslogMessageGenerator() {
		syslog = Syslog.getInstance("udp");
		syslog.getConfig().setUseStructuredData(false);
		syslog.getConfig().setHost("localhost");
		syslog.getConfig().setFacility(SyslogIF.FACILITY_LOCAL0);
		count = DEFAULT_COUNT;
		delay = DEFAULT_DELAY;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	/**
	 * Sets the host on the syslog message
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		syslog.getConfig().setHost(host);
	}
	
	/**
	 * Sets the facility on the syslog message
	 * 
	 * @param facility
	 */
	public void setFacility(int facility) {
		syslog.getConfig().setFacility(facility);
	}
	
	/**
	 * Set the port to send the syslog message
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		syslog.getConfig().setPort(port);
	}
	
	/**
	 * Sends syslog messages with values set for count and delay.
	 * 
	 * @throws InterruptedException
	 */
	public void sendMessages() throws InterruptedException {
		sendMessages(count,delay);
	}
	
	/**
	 * Sends syslog messages with the specified count and delay.
	 * 
	 * @param count
	 * @param delay
	 * @throws InterruptedException
	 */
	public void sendMessages(int count,int delay) throws InterruptedException {
		for (int n = count ; n != 0 ; n--) {
			
			syslog.info(new Date() + "TESTING------TESTING-----TESTING-----TESTING");
			if (n != count && n % 10 == 0) {
				LOG.info("Sent " + (count - n) + " messages");
			}
			Thread.sleep(delay);
		}
		LOG.info("Sent " + count + " messages");

	}
	
	/**
	 * 
	 * @param args
	 */
	void handleArguments(String[] args) {
		
		if (args.length > 3) {
			System.err.println("usage: " + (args.length == 1 ? args[0] : "") + " [count] [delay]");
			System.exit(1);
		}
		else if (args.length == 3) {
			setCount(Integer.parseInt(args[1]));
			setDelay(Integer.parseInt(args[2]));
		}
	}
	
	/**
	 * Sends syslog messages with the number sent and delayed between each
	 * passed in as arguments
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		SyslogMessageGenerator generator = new SyslogMessageGenerator();
		generator.handleArguments(args);
		
		generator.sendMessages();
	}
}
