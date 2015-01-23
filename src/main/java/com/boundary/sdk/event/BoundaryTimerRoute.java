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
package com.boundary.sdk.event;

import java.util.Date;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

public class BoundaryTimerRoute extends RouteBuilder {
	
	private int delay=10000;
	
	public BoundaryTimerRoute(int delay) {
		this.delay = delay;
	}

	@Override
	public void configure() throws Exception {
		from("timer:boundary?delay=" + this.delay).process(new Processor() {
			public void process(Exchange exchange) throws Exception {
				System.out.println(this.getClass().getName() + ": Invoked timer at " + new Date());
			}
		}).beanRef("boundary")
		.to("direct:event")
		;
	}
}