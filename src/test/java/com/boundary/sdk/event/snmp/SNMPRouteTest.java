/**
 * 
 */
package com.boundary.sdk.event.snmp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.sdk.SNMPRouteBuilder;

/**
 * @author davidg
 *
 */
public class SNMPRouteTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testMibRepository() {
		String expectedPath = "foobar";
		SNMPRouteBuilder builder = new SNMPRouteBuilder();
		
		builder.setMibRepository(expectedPath);
		assertEquals("Check license",expectedPath,builder.getMibRepository());
	}

	@Test
	public void testLicense() {
		String expectedLicense = "foobar";
		SNMPRouteBuilder builder = new SNMPRouteBuilder();
		
		builder.setLicense(expectedLicense);
		assertEquals("Check license",expectedLicense,builder.getLicense());
	}

}
