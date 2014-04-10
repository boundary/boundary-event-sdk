package com.boundary.sdk;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

public class TestDateParse {

	@Test
	public void testParseExample() {
		String syslogDate = "Wed Mar 12 13:27:13 PDT 2014";
		
		try {
			Date d;
			DateFormat df;
			df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
			d = df.parse("Wednesday, April 12, 2006 2:22:22 o'clock PM EDT");
			df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
			d = df.parse("12-Apr-06 2:22:22 PM");
			df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
			d = df.parse("April 12, 2006 2:22:22 PM EDT");
			// throws a ParseException; detail level mismatch
			d = df.parse("12-Apr-06 2:22:22 PM");
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}
	
	@Test
	public void testSysLogDateParse() {
		Date d;
		DateFormat df;

		df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

		
	}
}
