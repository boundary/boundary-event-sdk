// Copyright 2014-2015 Boundary, Inc.
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
package com.boundary.sdk.snmp.metric;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.boundary.sdk.event.snmp.SnmpPollerConfiguration;
import com.boundary.sdk.snmp.metric.SnmpMetricCatalog;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SnmpMetricCatalogTest {


	@Test
	public void testLoad() throws Exception {
		SnmpMetricCatalog catalog = new SnmpMetricCatalog();
		
		List<SnmpPollerConfiguration> configs = catalog.load();
		assertNotNull("check for configuration null",configs);
		assertEquals("check configuration size",1,configs.size());
		SnmpPollerConfiguration conf = configs.get(0);
		assertEquals("check configuration host","localhost",conf.getHost());
	}
	
	@Test
	public void testGetOidMap() throws Exception {
		SnmpMetricCatalog catalog = new SnmpMetricCatalog();
		
		List<SnmpPollerConfiguration> configs = catalog.load();
		SnmpPollerConfiguration config = configs.get(0);
		Map<String, OidMap> map = config.getOidMap();
		assertEquals("check OidMap size()",110,map.size());
		OidMap oid = map.get("1.3.6.1.2.1.4.3.0");
		assertNotNull("check for not null",oid);
		assertTrue("check enabled",oid.isEnabled());
	}
}
