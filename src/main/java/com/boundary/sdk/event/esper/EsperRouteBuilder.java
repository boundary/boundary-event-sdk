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
package com.boundary.sdk.event.esper;

import static org.apache.camel.LoggingLevel.DEBUG;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.BoundaryRouteBuilder;
import com.espertech.esper.client.EventBean;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of a Esper streaming route
 */
public class EsperRouteBuilder extends BoundaryRouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(EsperRouteBuilder.class);
	
	private String configuration;
	private String instance;
	private QueryList queryList;
	
	public EsperRouteBuilder() {
	}
	
	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	
	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	protected QueryList getQueryList() {
		return this.queryList;
	}
	
	private QueryList load(URI uri) {
		ObjectMapper mapper = new ObjectMapper();
		QueryList queryList = null;
		LOG.info("Loading queries from {}",uri);
		File file = new File(uri);

		try {
			queryList = mapper.readValue(file,QueryList.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return queryList;
	}

	/**
	 * TODO: Proper handling of exception
	 * @throws URISyntaxException {@link URISyntaxException}
	 */
	protected void loadConfiguration() throws URISyntaxException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL url = classLoader.getResource(this.configuration);
		this.queryList = load(url.toURI());
	}

	public void configure() throws Exception {
		String inputUri = String.format("esper://%s?configured=true",this.getInstance());
		
        from(this.getFromUri())
		.startupOrder(this.getStartUpOrder())
		.routeId(this.getRouteId() + "-INPUT")
		.unmarshal().serialization()
		.log(DEBUG,"headers: ${headers} body; ${body}, class: ${body.getClass.toString}")
        .to(inputUri);
        
        addQueriesToRoute();
    }
	
	private void addQueriesToRoute() throws URISyntaxException {
		loadConfiguration();
		QueryList list = this.getQueryList();
		
		for (Query query : list.getQueries()) {
			if (query.isEnabled()) {
				addQuery(query.getName(),query.getQuery());
			}
		}
	}
    
    private void addQuery(String name,String query) {
		String queryUri = String.format("esper://%s?configured=true&eql=%s",
				this.getInstance(),query);
		this.startUpOrder++;
		
		LOG.info("Adding query \"{}\": \"{}\"",name,query);
		
        from(queryUri)
        .startupOrder(this.getStartUpOrder())
        .routeId(String.format("%s-QUERY: %s",this.getRouteId(),name))
        .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                    	Message in = exchange.getIn();
                        EventBean event = in.getBody(EventBean.class);
                        Object e = event.getUnderlying();
                        in.setBody(e);
                    }
                })
        .log(DEBUG,"headers: ${headers} body; ${body}, class: ${body.getClass.toString}")
        .marshal().serialization()
        .to(this.toUri);
    }
}
