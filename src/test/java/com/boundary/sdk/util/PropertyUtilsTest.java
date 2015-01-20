package com.boundary.sdk.util;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.boundary.sdk.event.snmp.SnmpToExecProcessor.*;
import static com.boundary.sdk.event.util.PropertyUtils.getProperties;

public class PropertyUtilsTest {

	private static Logger LOG = LoggerFactory.getLogger(PropertyUtilsTest.class);

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
	
	private static Properties metricNameMap = new Properties();
	
	protected static String getMetricName(String severity) {
		getProperties(metricNameMap,"snmp.metric.names.properties");
		return metricNameMap.getProperty(severity.toString());
	}

	@Test
	public void test() {
		assertEquals("Not found in properties file","SYSTEM_PROC_TOTAL",getMetricName("hrSystemProcesses.0"));
		assertEquals("Not found in properties file","SYSTEM_NET_FLOWS_TCP_CONCURRENT_TOTAL",getMetricName("tcpCurrEstab.0"));
		assertEquals("Not found in properties file","SYSTEM_USER_LOGINS",getMetricName("hrSystemNumUsers.0"));
		assertEquals("Not found in properties file","UDP_IN_DATAGRAMS",getMetricName("udpInDatagrams.0"));
		assertEquals("Not found in properties file","UDP_OUT_DATAGRAMS",getMetricName("udpOutDatagrams.0"));
		assertEquals("Not found in properties file","TCP_IN_SEGS",getMetricName("tcpInSegs.0"));
		assertEquals("Not found in properties file","TCP_OUT_SEGS",getMetricName("tcpOutSegs.0"));
		assertEquals("Not found in properties file","IP_IN_RECEIVES",getMetricName("ipInReceives.0"));
		assertEquals("Not found in properties file","IP_OUT_REQUESTS",getMetricName("ipOutRequests.0"));
	}

}
