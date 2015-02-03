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
package com.boundary.sdk.event.syslog;


import java.util.Date;

import org.productivity.java.syslog4j.Syslog;
import org.productivity.java.syslog4j.SyslogIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * @param host Host or IP address of syslog listener
	 */
	public void setHost(String host) {
		syslog.getConfig().setHost(host);
	}
	
	/**
	 * Sets the facility on the syslog message
	 * 
	 * @param facility Syslog facility
	 */
	public void setFacility(int facility) {
		syslog.getConfig().setFacility(facility);
	}
	
	/**
	 * Set the port to send the syslog message
	 * 
	 * @param port Port of the syslog listener
	 */
	public void setPort(int port) {
		syslog.getConfig().setPort(port);
	}
	
	/**
	 * Sends syslog messages with values set for count and delay.
	 * 
	 * @throws InterruptedException {@link Thread} is interrupted
	 */
	public void sendMessages() throws InterruptedException {
		sendMessages(count,delay);
	}
	
	/**
	 * Sends syslog messages with the specified count and delay.
	 * 
	 * @param count Number of syslog messages to send
	 * @param delay Milliseconds to wait between sending of syslog messages
	 * @throws InterruptedException Thread is interrupted
	 */
	public void sendMessages(int count,int delay) throws InterruptedException {
		for (int n = count ; n != 0 ; n--) {
			
			syslog.info(new Date() + "TEST MESSAGE");
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
	 * passed in as arguments.
	 * 
	 * @param args Command line arguments
	 * @throws InterruptedException Thread was interrupted
	 */
	public static void main(String[] args) throws InterruptedException {

		SyslogMessageGenerator generator = new SyslogMessageGenerator();
		generator.handleArguments(args);
		
		generator.sendMessages();
	}
}
