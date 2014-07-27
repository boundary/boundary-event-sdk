// Copyright 2014 Boundary, Inc.
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.Status;
import static com.boundary.sdk.event.notification.NotificationTestUtil.*;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 *
 */
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
	
	/**
	 * Helper function for reading JSON examples
	 * 
	 * @param path Location of the file
	 * @param encoding Encoding used to read file
	 * @return {@String} Contents of file
	 * @throws IOException
	 */
	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	@Test
	public void testReadFile() throws IOException {
		String resource = readFile(NOTIFICATION_BASIC_JSON,Charset.defaultCharset());
		assertNotNull("Output is null",resource);
	}

	@Test
	public void testSimpleJsonToNotification() throws IOException {
		String expectedContents="Notification [event=null, filterId=e3c045ec-8028-48ce-9373-93e5b01c690c, filterName=Pester Michael about Critical events, notificationId=4ba705f6-690c-4877-b041-791b84e1e032]";
		String s = readFile(NOTIFICATION_BASIC_JSON,Charset.defaultCharset());
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		Notification notification = mapper.readValue(s, Notification.class);
		System.out.println("notification: " + notification);
		assertEquals("Notification output incorrect",expectedContents,notification.toString());
	}

	@Test
	public void testUnMarshallingJsonSimple() throws InterruptedException, IOException {
		out.setExpectedMessageCount(1);
		String s = readFile(NOTIFICATION_BASIC_JSON,Charset.defaultCharset());
		in.sendBody(s);
		out.await(5,TimeUnit.SECONDS);
		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			Notification notif = message.getBody(Notification.class);
			assertEquals("filterId does not match","e3c045ec-8028-48ce-9373-93e5b01c690c",notif.getFilterId());
			assertEquals("filterName does not match","Pester Michael about Critical events",notif.getFilterName());
		    assertEquals("notificationId does not match","4ba705f6-690c-4877-b041-791b84e1e032",notif.getNotificationId());
		}
	}
	@Test
	public void testUnMarshallingJsonComplete() throws InterruptedException, IOException {
		out.setExpectedMessageCount(1);
		String s = readFile(NOTIFICATION_JSON,Charset.defaultCharset());
		in.sendBody(s);
		out.await(5,TimeUnit.SECONDS);
		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			Notification notif = message.getBody(Notification.class);
			assertEquals("filterId does not match","e3c045ec-8028-48ce-9373-93e5b01c690c",notif.getFilterId());
			assertEquals("filterName does not match","Pester Michael about Critical events",notif.getFilterName());
		    assertEquals("notificationId does not match","4ba705f6-690c-4877-b041-791b84e1e032",notif.getNotificationId());
		    Event event = notif.getEvent();
		    assertNotNull("event is null",event);
//	        "properties": {
//	            "generator": "Boundary Event Console",
//	            "links": []
//	        },
		    
		    List<String> fingerprintFields = new ArrayList<String>();
		    fingerprintFields.add("generator");
		    fingerprintFields.add("@title");
		    Source source = new Source("DkQ2uOYtw0DyII696fpBUzIUMfs","organization","Lonesome no More, Inc.");
		    Status status = Status.OPEN;

		    Map<String,Object> properties = new HashMap<String,Object>();
		    properties.put("generator", "Boundary Event Console");
		    List<String> empty = new ArrayList<String>();
		    properties.put("links",empty);
		    Calendar cal = Calendar.getInstance();
		    cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		    cal.set(2014, 6, 17, 16, 54, 5);
		    Date firstSeenAt = cal.getTime();
		    Date lastUpdatedAt = cal.getTime();
//		    DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
//		    System.out.println( df.format(firstSeenAt));


		    assertEquals("fingerprint does not match","cM7tcSWntNfGDDQcvfS6csLNKyQ=",event.getFingerprint());
		    assertEquals("fingerprint fields do not match",fingerprintFields,event.getFingerprintFields());
		    assertEquals("id does not match","323515771",event.getId());
		    // TODO: Fix asserts for date fields
//		    assertEquals("firstSeenAt does not match",firstSeenAt,lastUpdatedAt);
//		    assertEquals("lastSeenAt does not match","2014-07-17T16:54:05.968Z",event.getLastSeenAt().toString());
//		    assertEquals("lastUpdatedAt does not match","2014-07-17T16:54:05.986Z",event.getLastUpdatedAt().toString());
		    assertEquals("message does not match","Hello World",event.getMessage());
		    assertEquals("organizationId does not match","DkQ2uOYtw0DyII696fpBUzIUMfs",event.getOrganizationId());
		    assertEquals("properties do not match",properties,event.getProperties());
		    assertEquals("severity does not match",Severity.CRITICAL,event.getSeverity());
		    assertEquals("source does not match",source.toString(),event.getSource().toString());
		    assertEquals("status does not match",status,event.getStatus());
		    assertEquals("timesSeen does not match",1,event.getTimesSeen());
		    assertEquals("title does not match","Critical Test Event",event.getTitle());


	
		    System.out.println(event);
		}
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/json-to-notification.xml");
	}
}
