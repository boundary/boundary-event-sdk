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

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.exec.ExecBinding;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_ARGS;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_EXECUTABLE;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_TIMEOUT;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_WORKING_DIR;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;


public class ExecSetHeadersTest extends CamelTestSupport {
	
    @Produce(uri = "direct:in")
    protected ProducerTemplate in;
    @EndpointInject(uri = "mock:out")
    protected MockEndpoint out;
 
	@Test
	public void test() throws InterruptedException {
		String executable = "echo";
		String args = "hello";
		int timeout = 10;
		File currentDir = new File("");
		Exec exec = new Exec(executable,
							args,
							currentDir.getAbsolutePath(),
							timeout);
		
        out.expectedMessageCount(1);
        
    	in.sendBody(exec);
 
        out.assertIsSatisfied();

        List<Exchange> exchanges = out.getExchanges();
        Exchange exchange = exchanges.get(0);
        Message message = exchange.getIn();
        assertEquals("check executable",executable,message.getHeader(EXEC_COMMAND_EXECUTABLE));
        Object newArgs = message.getHeader(EXEC_COMMAND_ARGS);
        assertEquals("check arg class","class java.lang.String",newArgs.getClass().toString());
        assertEquals("check args",args,message.getHeader(EXEC_COMMAND_ARGS).toString());
        assertEquals("check current directory",currentDir.getAbsolutePath(),
        		message.getHeader(EXEC_COMMAND_WORKING_DIR));
        assertEquals("check timeout",timeout,message.getHeader(EXEC_COMMAND_TIMEOUT));
	}
	
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:in")
                .process(new ExecSetHeaders())
                .to("mock:out");
            }
        };
    }
    
    public static void main(String [] args) {
    	File currentDir = new File("");
    	System.out.println(currentDir.getAbsolutePath());
    }

}
