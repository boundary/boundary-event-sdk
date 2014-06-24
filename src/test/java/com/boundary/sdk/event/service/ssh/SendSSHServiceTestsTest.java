package com.boundary.sdk.event.service.ssh;

import static org.junit.Assert.*;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.sdk.event.service.ssh.SendSSHServiceTests;

public class SendSSHServiceTestsTest extends CamelSpringTestSupport {
	
	private SendSSHServiceTests configuration;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		configuration = new SendSSHServiceTests();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testSetTestName() {
		String testName = "foobar";
		configuration.setServiceTestName(testName);
		assertEquals("check test name",testName,configuration.getServiceTestName());
	}

	@Test
	public void testSetServiceName() {
		String serviceName = "happy days";
		configuration.setServiceName(serviceName);
		assertEquals("check test name",serviceName,configuration.getServiceName());
	}

	@Test
	public void testSetRequestId() {
		String requestId = "DEADBEEF";
		configuration.setRequestId(requestId);
		assertEquals("check test name",requestId,configuration.getRequestId());
	}

	@Test
	public void testSetHost() {
		String host = "google.com";
		configuration.setHost(host);
		assertEquals("check test name",host,configuration.getHost());
	}

	@Test
	public void testSetUser() {
		String user = "tmontana";
		configuration.setUser(user);
		assertEquals("check test name",user,configuration.getUser());
	}

	@Test
	public void testSetPassword() {
		String password = "king of the world";
		configuration.setPassword(password);
		assertEquals("check test name",password,configuration.getPassword());
	}

	@Test
	public void testSetCommand() {
		String command = "ls | grep foo | more";
		configuration.setCommand(command);
		assertEquals("check test name",command,configuration.getCommand());
	}
	
	@Test
	public void testExpectedOutput() {
		String expectedOutput = "hello world";
		configuration.setExpectedOutput(expectedOutput);
		assertEquals("check test name",expectedOutput,configuration.getExpectedOutput());
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
