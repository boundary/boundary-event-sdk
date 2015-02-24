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
package com.boundary.sdk.event.notification;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlarmNotificationTest {
	
	private final static String ALARM_NOTIFICATION_JSON_FILE = "META-INF/json/multi-triggered-notification.json";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoad() throws URISyntaxException {
		AlarmNotification notification = AlarmNotification.load(ALARM_NOTIFICATION_JSON_FILE);
		assertNotNull("Check for null",notification);
	}
	
	@Test
	public void testNotification() throws URISyntaxException {
		AlarmNotification notification = AlarmNotification.load(ALARM_NOTIFICATION_JSON_FILE);
		
		assertEquals("Check affected server count",3,notification.getAffectedServers().size());
		assertEquals("Check resolved server count",0,notification.getResolvedServers().size());

		assertEquals("Check alarmName","CPU usage is high",notification.getAlarmName());

	}
	
	@Test
	public void testNotificationMetric() throws URISyntaxException {
		AlarmNotification notification = AlarmNotification.load(ALARM_NOTIFICATION_JSON_FILE);
		
		Metric metric = notification.getMetric();
		assertEquals("Check metric id","cpu",metric.getId());
		assertEquals("Check metric name","CPU utilization",metric.getName());
		assertEquals("Check metric type","percent",metric.getType());
	}
	
	@Test
	public void testNotificationServers() throws URISyntaxException {
		AlarmNotification notification = AlarmNotification.load(ALARM_NOTIFICATION_JSON_FILE);

		for (Entry<String,Server> entry : notification.getAffectedServers().entrySet()) {
			Server server = entry.getValue();
			assertEquals("Check host name",entry.getKey(),server.getHostname());

			switch (entry.getKey()) {
			
			case "boundary-snmp-001":
				assertEquals("Check host name","https://premium.boundary.com/2105/default?ival=60&marker=1424793480000!cpu",server.getLink());
				assertEquals("Check threshold",0.8,server.getThreshold());
				assertEquals("Check time",1424793480000L,server.getTime().getTime());
				assertEquals("Check value",0.9998737333333333,server.getValue());
				Text text1 = server.getText();
				assertEquals("check labelHTML","Server <strong>boundary-snmp-001</strong>'s avg CPU utilization is <strong style=\"color:#900\">100.0%</strong> which is greater than the threshold of 80.0%",text1.getLabelHTML());
				assertEquals("check labelText","Server boundary-snmp-001's avg CPU utilization is 100.0% which is greater than the threshold of 80.0%",text1.getLabelText());
				break;
			case "boundary-snmp-002":
				assertEquals("Check host name","https://premium.boundary.com/2105/default?ival=60&marker=1424793480000!cpu",server.getLink());
				assertEquals("Check threshold",0.8,server.getThreshold());
				assertEquals("Check time",1424793480000L,server.getTime().getTime());
				assertEquals("Check value",0.9995898666666665,server.getValue());
				Text text2 = server.getText();
				assertEquals("check labelHTML","Server <strong>boundary-snmp-002</strong>'s avg CPU utilization is <strong style=\"color:#900\">100.0%</strong> which is greater than the threshold of 80.0%",text2.getLabelHTML());
				assertEquals("check labelText","Server boundary-snmp-002's avg CPU utilization is 100.0% which is greater than the threshold of 80.0%",text2.getLabelText());
				break;
			case "boundary-snmp-003":
				assertEquals("Check host name","https://premium.boundary.com/2105/default?ival=60&marker=1424793480000!cpu",server.getLink());
				assertEquals("Check threshold",0.8,server.getThreshold());
				assertEquals("Check time",1424793480000L,server.getTime().getTime());
				assertEquals("Check value",0.9998349833333333,server.getValue());
				Text text3 = server.getText();
				assertEquals("check labelHTML","Server <strong>boundary-snmp-003</strong>'s avg CPU utilization is <strong style=\"color:#900\">100.0%</strong> which is greater than the threshold of 80.0%",text3.getLabelHTML());
				assertEquals("check labelText","Server boundary-snmp-003's avg CPU utilization is 100.0% which is greater than the threshold of 80.0%",text3.getLabelText());
				break;
			default:
				break;
			}
		}
	}

}
