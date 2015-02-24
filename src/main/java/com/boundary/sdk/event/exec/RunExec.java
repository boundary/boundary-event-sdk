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
package com.boundary.sdk.event.exec;

import static org.apache.camel.LoggingLevel.INFO;

import org.apache.camel.model.RouteDefinition;

import com.boundary.sdk.event.BoundaryRouteBuilder;

public class RunExec extends BoundaryRouteBuilder {

	@Override
	public void configure() throws Exception {
		RouteDefinition routeDef = from(getFromUri())
		.routeId(this.getRouteId())
		.startupOrder(this.getStartUpOrder())
		.process(new ExecSetHeaders())
		.log(INFO,"Exec ${headers.CamelExecCommandExecutable} exit code: ${headers.CamelExecExitValue}");

		if (this.getToUri() != null && this.getToUri().length() > 0) {
			routeDef.to(getToUri());
		}
	}
}
