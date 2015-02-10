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
package com.boundary.sdk.event.snmp;

import static org.apache.camel.LoggingLevel.DEBUG;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.mp.SnmpConstants;

import com.boundary.sdk.snmp.metric.SnmpMetricCatalog;

public class SnmpPollerRouteBuilder extends SnmpTrapRouteBuilder {
	
	public final static String BOUNDARY_SNMP_POLLER_CONFIG="boundary.snmp.poller.configuration";
	
	private static Logger LOG = LoggerFactory.getLogger(SnmpPollerRouteBuilder.class);
	
	private SnmpMetricCatalog catalog;
	private List<SnmpPollerConfiguration> configList;
	
	public SnmpPollerRouteBuilder() {
		catalog = new SnmpMetricCatalog();
	}
	
	public void loadConfiguration() throws Exception {
		this.configList = this.catalog.load();
	}

	private String getUri(
			String host,
			long port,
			String oids,
			String community,
			long delay) {
		StringBuffer sb = new StringBuffer();
		sb.append("snmp:");
		sb.append(host);
		sb.append(":");
		sb.append(port);
		sb.append("?protocol=udp");
		sb.append("&type=POLL");
		sb.append("&oids=" + oids);
		sb.append("&snmpVersion=" + SnmpConstants.version2c);
		sb.append("&snmpCommunity=" + community);
		sb.append("&delay="+delay);
		LOG.debug("from: " + sb.toString());
		return sb.toString();
	}
	
	/**
	 * Configuration for the SNMP route 
	 */
	@Override
	public void configure() {
		int startUpOrder = this.getStartUpOrder();

		try {
			this.loadConfiguration();
			LOG.info("Configuration contains {} pollers to start",this.configList.size());
			for (SnmpPollerConfiguration config : this.configList) {
				                                                                                 
				LOG.info("Create route for host: {}",config.getHost());

				String fromUri = getUri(config.getHost(), config.getPort(),
						config.getOidsAsString(), config.getCommunityRead(),
						config.getDelay());

				from(fromUri)
					.routeId(this.routeId)
					.startupOrder(startUpOrder++)
					.process(new SnmpMessageToVarBinds(getMibRepository(),getLicense()))
					.log(DEBUG,"Before split - body: ${body}")
					.split().method(new SplitVarBinds(),"splitBody")
					.setHeader(BOUNDARY_SNMP_POLLER_CONFIG,constant(config))
					.process(new SnmpHandleException())
					.log(DEBUG,"After split - body: ${body}")
					.marshal().serialization()
					.to(this.getToUri())
					.end();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
