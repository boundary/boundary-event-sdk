package com.boundary.sdk.event.syslog;


import java.util.Date;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;

public class SyslogMessageGenerator {
	
	
	SyslogIF syslog;

	public SyslogMessageGenerator() {
		syslog = Syslog.getInstance("udp");
		syslog.getConfig().setUseStructuredData(false);
		syslog.getConfig().setHost("localhost");
		syslog.getConfig().setFacility(SyslogIF.FACILITY_LOCAL0);
		syslog.getConfig().setPort(1514);
	}
	
	/**
	 * Sets the host on the syslog message
	 * 
	 * @param host
	 */
	void setHost(String host) {
		syslog.getConfig().setHost(host);
	}
	
	/**
	 * Sets the facility on the syslog message
	 * 
	 * @param facility
	 */
	void setFacility(int facility) {
		syslog.getConfig().setFacility(facility);
	}
	
	void sendMessages(int count,int delay) throws InterruptedException {
		for (int n = count ; n != 0 ; n--) {
			syslog.info(new Date() + "TESTING------TESTING-----TESTING-----TESTING");
			Thread.sleep(delay);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		SyslogMessageGenerator generator = new SyslogMessageGenerator();
		
		generator.sendMessages(100,10);

	}
}
