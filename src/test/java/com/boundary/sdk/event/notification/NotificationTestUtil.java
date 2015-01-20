package com.boundary.sdk.event.notification;

import static org.junit.Assert.*;

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

import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.Status;

public class NotificationTestUtil {
	
	public final static String NOTIFICATION_BASIC_JSON="src/test/resources/META-INF/json/simple-notification.json";
	public final static String NOTIFICATION_JSON="src/test/resources/META-INF/json/notification.json";
	public final static String NOTIFICATION_FULL_JSON="src/test/resources/META-INF/json/full-notification.json";

	
	/**
	 * Helper function for reading JSON examples
	 * 
	 * @param path Location of the file
	 * @param encoding Encoding used to read file
	 * @return {@String} Contents of file
	 * @throws IOException
	 */
	static public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	static void validateNotification(Notification notif) {
		assertEquals("filterId does not match","e3c045ec-8028-48ce-9373-93e5b01c690c",notif.getFilterId());
		assertEquals("filterName does not match","Pester Michael about Critical events",notif.getFilterName());
	    assertEquals("notificationId does not match","4ba705f6-690c-4877-b041-791b84e1e032",notif.getNotificationId());
	    Event event = notif.getEvent();
	    assertNotNull("event is null",event);
//        "properties": {
//            "generator": "Boundary Event Console",
//            "links": []
//        },
	    
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
//	    DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
//	    System.out.println( df.format(firstSeenAt));


	    assertEquals("fingerprint does not match","cM7tcSWntNfGDDQcvfS6csLNKyQ=",event.getFingerprint());
	    assertEquals("fingerprint fields do not match",fingerprintFields,event.getFingerprintFields());
	    assertEquals("id does not match","323515771",event.getId());
	    // TODO: Fix asserts for date fields
//	    assertEquals("firstSeenAt does not match",firstSeenAt,lastUpdatedAt);
//	    assertEquals("lastSeenAt does not match","2014-07-17T16:54:05.968Z",event.getLastSeenAt().toString());
//	    assertEquals("lastUpdatedAt does not match","2014-07-17T16:54:05.986Z",event.getLastUpdatedAt().toString());
	    assertEquals("message does not match","Hello World",event.getMessage());
	    assertEquals("organizationId does not match","DkQ2uOYtw0DyII696fpBUzIUMfs",event.getOrganizationId());
	    assertEquals("properties do not match",properties,event.getProperties());
	    assertEquals("severity does not match",Severity.CRITICAL,event.getSeverity());
	    assertEquals("source does not match",source.toString(),event.getSource().toString());
	    assertEquals("status does not match",status,event.getStatus());
	    assertEquals("timesSeen does not match",1,event.getTimesSeen());
	    assertEquals("title does not match","Critical Test Event",event.getTitle());
	}
}
