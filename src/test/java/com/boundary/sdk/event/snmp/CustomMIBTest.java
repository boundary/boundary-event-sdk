package com.boundary.sdk.event.snmp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.snmp4j.SNMP4JSettings;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;

import com.snmp4j.smi.SmiManager;
import com.snmp4j.smi.SmiObject;

public class CustomMIBTest {
	
	private static final String BOUNDARY_MIB_MODULE_NAME = "BOUNDARY-MIB-INTERNAL";
	private static final String BOUNDARY_MIB_FILE_NAME = "src/test/snmp/BOUNDARY-MIB.txt";
	private SmiSupport support;
	private SmiManager smiManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		support = new SmiSupport();
		smiManager = support.getSmiManager();
		support.setRepository("src/main/resources/mibrepository");
		support.setLicense(null);
		support.initialize();
		//SNMP4JSettings.setOIDTextFormat(smiManager);
	    //SNMP4JSettings.setVariableTextFormat(smiManager);


	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Ignore("Requires SNMP4J License")
	@Test
	public void testCompile() throws IOException {
		support.compile(BOUNDARY_MIB_FILE_NAME);
	}

	@Ignore("Requires SNMP4J License")
	@Test
	public void testLoadModules() {
		support.loadModules();
	}
	
	@Ignore("Requires SNMP4J License")
	@Test
	public void testDumpOIDs() {
		String[] oids = {
				"1.3.6.1",
				"1.3.6.1.4.1.3.1.1",
				"1.3.6.1.2.1.1.3.0",
				"1.3.6.1.6.3.1.1.4.1.0",
				"1.3.6.1.6.3.1.1.4.1",
				"1.3.6.1.2.1.1.5.0",
				"1.2.3.4.5",
				"1.3.6.1.2.1.1.1.0",
				"0.0"};
		
		OID oid = new OID();
		for (String o :oids) {
			oid.setValue(o);
			System.out.println(o + " = " + oid);
			System.out.println(oid.getSyntaxString());
			//System.out.println(oid.format());
		}
		System.out.println(SnmpConstants.snmpTrapOID);
		System.out.println("SnmpConstants.snmpTrapEnterprise: " + SnmpConstants.snmpTrapEnterprise);
		System.out.println(SnmpConstants.snmpTrapOID.toDottedString());
		System.out.println(new OID(SnmpConstants.snmpTrapOID.toDottedString() + ".3"));
		System.out.print(SnmpConstants.linkDown.toDottedString());


	}
	
	@Ignore("Requires SNMP4J License")
	@Test
	public void testLookUpEnterpriseTrap() {
		OID oid = new OID();
		oid.setValue("1.3.6.1.6.3.1.1.4.3.0.24927.8.1.1");
		//SmiObject o = smiManager.findSmiObject(oid);
		//System.out.println(o.getDescription());
	}
}
