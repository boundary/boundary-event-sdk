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
package com.boundary.sdk.event.script;

import static org.apache.camel.LoggingLevel.DEBUG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.BoundaryRouteBuilder;

public class ScriptRouteBuilder extends BoundaryRouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(ScriptRouteBuilder.class);

	private String languageName;
	private boolean transform;
	private String script;
	private boolean cacheScriptEnabled;
	private boolean contentCacheEnabled;
	
	public ScriptRouteBuilder() {
		this.languageName = "javascript";
		this.transform = true;
		this.cacheScriptEnabled = true;
		this.contentCacheEnabled = true;
	}
	
	protected String getLanguageToUri() {
		String toUri = String.format(
				"language:%s:%s?transform=%s&contentCache=%s&cacheScript",
				this.getLanguageName(),
				this.getScript(),
				this.getTransform(),
				this.getContentCache(),
				this.getCacheScript());
		return toUri;
	}
	/**
	 * 
	 */
	@Override
	public void configure() {
		LOG.info("Route using script: {}",this.getScript());
		from(this.getFromUri())
		.routeId(this.getRouteId())
		.startupOrder(this.getStartUpOrder())
		.unmarshal().serialization()
		.log(DEBUG,"in: ${body}")
		.to(this.getLanguageToUri())
		.log(DEBUG,"out: ${body}")
		.marshal().serialization()
		.to(this.getToUri());
	}
	
	public boolean getCacheScript() {
		return this.cacheScriptEnabled;
	}
	
	public void setCacheScript(boolean cacheScriptEnabled) {
		this.cacheScriptEnabled = cacheScriptEnabled;
	}
	
	public boolean getContentCache() {
		return this.contentCacheEnabled;
	}
	
	public void setContentCache(boolean contentCacheEnabled) {
		this.contentCacheEnabled = contentCacheEnabled;
	}
	
	public boolean getTransform() {
		return this.transform;
	}
	
	public void setTransform(boolean transform) {
		this.transform = transform;
	}
	
	public String getScript() {
		return this.script;
	}
	
	public void setScript(String script) {
		this.script = script;
	}
	
	public String getLanguageName() {
		return this.languageName;
	}
	
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
}
