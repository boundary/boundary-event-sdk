package com.boundary.sdk.event.syslog;


import java.util.Date;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;

/**
 * Class to generate syslog messages for testing
 * 
 * @author davidg
 *
 */
public class SyslogMessageGenerator {
	
	
	private SyslogIF syslog;

	public SyslogMessageGenerator() {
		syslog = Syslog.getInstance("udp");
		syslog.getConfig().setUseStructuredData(false);
		syslog.getConfig().setHost("localhost");
		syslog.getConfig().setFacility(SyslogIF.FACILITY_LOCAL0);
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
	
	public void sendMessages(int count,int delay) throws InterruptedException {
		for (int n = count ; n != 0 ; n--) {
			
			syslog.info(new Date() + "TESTING------TESTING-----TESTING-----TESTING");
			if (n != count && n % 10 == 0) {
				System.out.println("Sent " + (count - n) + " messages");
			}
			Thread.sleep(delay);
		}
		System.out.println("Sent " + count + " messages");

	}
	
	public static void main(String[] args) throws InterruptedException {
		SyslogMessageGenerator generator = new SyslogMessageGenerator();
		
		generator.sendMessages(100,10);

	}
}
