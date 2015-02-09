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

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.snmp.SnmpPollerConfiguration;

/**
 * Master catalog of meta for SNMP metric collection
 */
public class SnmpMetricCatalog {
	
	private static Logger LOG = LoggerFactory.getLogger(SnmpMetricCatalog.class);

	private static final String SNMP_CONFIG_DIR_PROPERTY = "com.boundary.snmp.config.directory";
	private static final String POLLERS_CONFIG_FILENAME = "pollers.json";
	private static final String HOST_LISTS_CONFIG_FILENAME = "hosts.json";
	private static final String OID_LISTS_CONFIG_FILENAME = "oids.json";
	private OidMapList oidLists;
	private HostLists hostLists;
	private Pollers pollers;
	private String configDirectory;
	private String pollersResource;
	private String hostListsResource;
	private String oidListsResource;
	
	public SnmpMetricCatalog() {
		
		this.configDirectory = System.getProperty(SNMP_CONFIG_DIR_PROPERTY, "META-INF/json");
		this.pollersResource = this.configDirectory + "/" + POLLERS_CONFIG_FILENAME;
		this.hostListsResource = this.configDirectory + "/" + HOST_LISTS_CONFIG_FILENAME;
		this.oidListsResource = this.configDirectory + "/" + OID_LISTS_CONFIG_FILENAME;
	}
	
	private void readAndValidate() throws Exception {
		try {
			LOG.info("Loading pollers from {}",this.pollersResource);
			pollers = Pollers.load(this.pollersResource);
			LOG.info("Loading hosts from {}",this.hostListsResource);

			hostLists = HostLists.load(this.hostListsResource);
			LOG.info("Loading oids from {}",this.oidListsResource);
			oidLists = OidMapList.load(this.oidListsResource);
		} catch (URISyntaxException e) {
			throw new Exception("Configuration Error");
		}
		LOG.info("Configuration loaded",this.hostListsResource);
	}

	/**
	 * Loop over pollers and build a list of {@link SnmpPollerConfiguration}s
	 * @return List of {@link SnmpPollerConfiguration}s
	 * @throws Exception Any kind of error occurs
	 */
	public List<SnmpPollerConfiguration> load() throws Exception {
		List<SnmpPollerConfiguration> list = new ArrayList<SnmpPollerConfiguration>();
		
		this.readAndValidate();
		
		LOG.info("Loaded {} poller entries",pollers.getPollers().size());
		for (PollerEntry entry: pollers.getPollers()) {
			
			List<Host> hosts = hostLists.getHosts(entry.getHostListIds());
			LOG.info("Poller \"{}\" (id {}) contains {} host list(s)",
					entry.getName(),entry.getId(),hosts.size());
			for (Host host : hosts) {
				SnmpPollerConfiguration configuration = new SnmpPollerConfiguration();
				configuration.setDelay(entry.getDelay());
				
				configuration.setHost(host.getHost());
				configuration.setPort(host.getPort());
				configuration.setCommunityRead(host.getCommunityRead());
				
				List<OidMap> oids = oidLists.getOids(entry.getOidListIds());
				configuration.setOids(oids);
				list.add(configuration);
			}
		}
		return list;
	}


}
