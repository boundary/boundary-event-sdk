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

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class EsperProcessor implements Processor {
	
	private Random random;
	
	int count;

	public EsperProcessor() {
		this.random = new Random();
		count = 0;
	}

	@Override
	public void process(Exchange arg0) throws Exception {
		
		Message in = arg0.getIn();
		EsperEvent e = new EsperEvent();
		if (count % 2 == 0) {
			e.setName("Bar");
		} else {
			e.setName("Foo");
		}
		count++;
		e.setValue(random.nextInt(10));
		
		in.setBody(e);
	}

}
