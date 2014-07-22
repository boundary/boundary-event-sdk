package com.boundary.sdk.event.snmp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.snmp4j.smi.OID;

import com.snmp4j.smi.SmiManager;

public class CustomMIBTest {
	
	SmiSupport support;
	SmiManager smiManager;

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
		support.setLicense("a8 29 19 b4 66 e5 4c 1f / LlSFSvNS");
		support.initialize();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadModule() {
		smiManager.loadModule(CUSTOM_MODULE_NAME);
	}
	
	public static void main(String []args) throws IOException {
		SmiSupport support = new SmiSupport();
		SmiManager smiManager = support.getSmiManager();
		
		support.setRepository("src/main/resources/mibrepository");
		support.setLicense("a8 29 19 b4 66 e5 4c 1f / LlSFSvNS");
		support.initialize();
		
		smiManager = support.getSmiManager();
		
		String [] modules = smiManager.listModules();
//		System.out.println(modules.length);
//		
//		System.out.println(new Date());
		
		for (String name :modules) {
			//System.out.println("Loading module: " + name);
			smiManager.loadModule(name);
		}
		
		System.out.println(new Date());
		
		String[] oids = {
				/* "1.3.6.1",
				"1.3.6.1.4.1.3.1.1",
				"1.3.6.1.2.1.1.3.0",
				"1.3.6.1.6.3.1.1.4.1.0",
				"1.3.6.1.6.3.1.1.4.1",
				"1.3.6.1.2.1.1.5.0",
				"1.2.3.4.5",
				"1.3.6.1.2.1.1.1.0",
				"0.0", */
				"enterprises.24927.8.1.1"};
		
		OID oid = new OID();
		for (String o :oids) {
			oid.setValue(o);
			System.out.println(o + " = " + oid);
			System.out.println(oid.getSyntaxString());
		}
		
//		System.out.println(SnmpConstants.snmpTrapOID);
//		System.out.println(SnmpConstants.snmpTrapOID.toDottedString());
//		System.out.println(new OID(SnmpConstants.snmpTrapOID.toDottedString() + ".3"));
//		System.out.print(SnmpConstants.linkDown.toDottedString());

	}

}
