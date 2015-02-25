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

import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_ARGS;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_EXECUTABLE;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_TIMEOUT;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_WORKING_DIR;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SplitExecsTest extends CamelTestSupport {
	
    @Produce(uri = "direct:in")
    protected ProducerTemplate in;
    @EndpointInject(uri = "mock:out")
    protected MockEndpoint out;
 


	@Test
	public void test1Exec() throws InterruptedException {
		ExecList list = new ExecList();
		String currentDirectory = new File("").getAbsolutePath();
		
		Exec exec = new Exec("ls",".",currentDirectory,5);
		list.add(exec);
		
        out.expectedMessageCount(1);
        
    	in.sendBody(list);
 
        out.assertIsSatisfied();

        List<Exchange> exchanges = out.getExchanges();
        Exchange exchange = exchanges.get(0);
        Message message = exchange.getIn();
        Exec newExec = message.getBody(Exec.class);
        assertNotNull("check for null exec",newExec);
        assertEquals("check executable","ls",newExec.getExecutable());
        assertEquals("check args",".",newExec.getArgs());
        assertEquals("check working directory",currentDirectory,newExec.getWorkingDirectory());
        assertEquals("check timeout",5,newExec.getTimeout());
	}
	
	@Test
	public void test3Exec() throws InterruptedException {
		ExecList list = new ExecList();
		String currentDirectory = new File("").getAbsolutePath();
		
		Exec exec1 = new Exec("ls",".",currentDirectory,5);
		list.add(exec1);
		Exec exec2 = new Exec("pwd",".","/tmp",10);
		list.add(exec2);
		Exec exec3 = new Exec("touch","myfile","/var/log",15);
		list.add(exec3);

        out.expectedMessageCount(3);
        
    	in.sendBody(list);
 
        out.assertIsSatisfied();

        // Exec 1
        List<Exchange> exchanges = out.getExchanges();
        Exchange exchange = exchanges.get(0);
        Message message = exchange.getIn();
        Exec newExec = message.getBody(Exec.class);
        assertNotNull("check for null exec",newExec);
        assertEquals("check executable","ls",newExec.getExecutable());
        assertEquals("check args",".",newExec.getArgs());
        assertEquals("check working directory",currentDirectory,newExec.getWorkingDirectory());
        assertEquals("check timeout",5,newExec.getTimeout());
        
        // Exec 2
        exchange = exchanges.get(1);
        message = exchange.getIn();
        newExec = message.getBody(Exec.class);
        assertNotNull("check for null exec",newExec);
        assertEquals("check executable","pwd",newExec.getExecutable());
        assertEquals("check args",".",newExec.getArgs());
        assertEquals("check working directory","/tmp",newExec.getWorkingDirectory());
        assertEquals("check timeout",10,newExec.getTimeout());

        // Exec 3
        exchange = exchanges.get(2);
        message = exchange.getIn();
        newExec = message.getBody(Exec.class);
        assertNotNull("check for null exec",newExec);
        assertEquals("check executable","touch",newExec.getExecutable());
        assertEquals("check args","myfile",newExec.getArgs());
        assertEquals("check working directory","/var/log",newExec.getWorkingDirectory());
        assertEquals("check timeout",15,newExec.getTimeout());

	}

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() {
                from("direct:in")
                .split().simple("${body.getExecList}")
                .to("mock:out");
            }
        };
    }
}
