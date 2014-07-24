package com.boundary.sdk.event.snmp;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.snmp4j.SNMP4JSettings;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;

import com.snmp4j.smi.SmiManager;
import com.snmp4j.smi.SmiObject;

public class CustomMIBTest {
	
	private static final String BOUNDARY_MIB_MODULE_NAME = "BOUNDARY-MIB-INTERNAL";
	
	private static final String SOURCE_MIB_DIR="src/test/snmp/SMIv2";
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	private static final String BOUNDARY_MIB_FILE_NAME = "src/test/snmp/BOUNDARY-MIB.txt";
	private static SmiSupport support;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		File mibDir= folder.newFolder("mib-repo");
		support = new SmiSupport();
		System.out.println(mibDir.toString());
		support.setRepository(mibDir.toString());
		support.setLicense(System.getenv("BOUNDARY_MIB_LICENSE"));
		support.initialize();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompileStandardMIBs() throws IOException {
		File mibDir= folder.newFolder("mib-repo");
		support = new SmiSupport();
		System.out.println(mibDir.toString());
		support.setRepository(mibDir.toString());
		support.setLicense(System.getenv("BOUNDARY_MIB_LICENSE"));
		support.initialize();
		support.setCompileLeniently(true);
		support.setLicense(null);
		support.compile(SOURCE_MIB_DIR);
	}
	
	//@Ignore("Requires SNMP4J License")
	@Test
	public void testCompile() throws IOException {
		File mibDir= folder.newFolder("mib-repo");
		support = new SmiSupport();
		System.out.println(mibDir.toString());
		support.setRepository(mibDir.toString());
		support.initialize();
		support.setCompileLeniently(true);
		support.compile(BOUNDARY_MIB_FILE_NAME);
	}
	
	@Ignore("Requires SNMP4J License")
	@Test
	public void testLoadModule() throws IOException {
		support.getSmiManager().loadModule("BOUNDARY-MIB-INTERNAL");
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
		System.out.println(SnmpConstants.linkDown.toDottedString());
	}
	
	@Ignore("Requires SNMP4J License")
	@Test
	public void testLookupDescription() {
		support.loadModules();
		
	}
	
	//@Ignore("Requires SNMP4J License")
	@Test
	public void testLookUpEnterpriseTrap() throws IOException {
		support.setCompileLeniently(true);
		support.compile(BOUNDARY_MIB_FILE_NAME);
		support.loadModules();
		OID oid = new OID();
		oid.setValue("enterprises.4.8.1.2");
		SmiManager smi = support.getSmiManager();
		System.out.println("oid: " + oid);

		SmiObject o = smi.findSmiObject(oid);
		
		System.out.println("getAsn1Comments()");
		for (String s : o.getAsn1Comments()) {
			System.out.println(s);
		}
		System.out.println("getChildren()");
		for (SmiObject co : o.getChildren()) {
			System.out.println(co.getOID());
		}

		System.out.println("getDescription(): " + o.getDescription());
		System.out.println("getObjectName(): " + o.getObjectName());
		System.out.println("getOID(): " + o.getOID());
		System.out.println("getOID().toDottedString(): " + o.getOID().toDottedString());
		System.out.println("getParent().getOID(): " + o.getParent().getOID());
		System.out.println("getReference: " + o.getReference());
		System.out.println("getSmiSyntax: " + o.getSmiSyntax());
		System.out.println("getStatus: " + o.getStatus());
		System.out.println("getType: " + o.getType());
		System.out.println(o);
	}
}
