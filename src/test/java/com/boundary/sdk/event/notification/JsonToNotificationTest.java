
package com.boundary.sdk.event.notification;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringTestSupport;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToNotificationTest extends CamelSpringTestSupport {
	
    @Produce(uri = "direct:json-in")
    private ProducerTemplate in;

    @EndpointInject(uri = "mock:notification-out")
    private MockEndpoint out;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void test() throws IOException, URISyntaxException {
		String s = readFile("META-INF/json/simple-notification.json",Charset.defaultCharset());
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		Notification notification = mapper.readValue(s, Notification.class);
		System.out.println("notification: " + notification);
	}

	public String readFile(String resource, Charset encoding) throws IOException, URISyntaxException {
		URL url = this.getClass().getResource(resource);
		byte[] encoded = Files.readAllBytes(Paths.get(url.toURI()));
		return new String(encoded, encoding);
	}
	
//	public static String readFile(File file, Charset encoding) throws IOException {
//		URL url = this.getClass().getResource(resource);
//		byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));
//		return new String(encoded, encoding);
//	}

	
	public File getFile(String resource) throws URISyntaxException {
		URL url = this.getClass().getResource(resource);
		File file = new File(url.toURI());
		return file;
	}
	
	@Test
	public void testJsonToNotification() {
		out.setExpectedMessageCount(1);
		
		in.sendBody("");
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/json-to-notification.xml");
	}
}
