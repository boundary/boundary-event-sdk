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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.boundary.sdk.event.BoundaryRouteBuilder;
import com.espertech.esper.client.EventBean;

/**
 * Implementation of a Esper streaming route
 */
public class EsperRouteBuilder extends BoundaryRouteBuilder {
	
    public void configure() {

        from(this.fromUri)
		.startupOrder(this.startUpOrder-1)
		.routeId(this.routeId + "-IN")
		.unmarshal().serialization()
        .to("esper://events?configured=true");
        
//        String query1 = "￼select count(*) as cnt from RawEvent where name = \"Foo\" having count(*) = 2";
//        String query2 = "select name,avg(value) from RawEvent where fingerPrintFields[0] = \"hostname\"";
//        String query3 = "select * from RawEvent where source.ref = \"lerma\"";
//        String query4 = "select name, count(*) as nmin from RawEvent.win:time_batch(60 sec) group by name";
//        String query5 = "select name, count(*) as nhr from RawEvent.win:time_batch(3600 sec) group by name";
        String query6 = "select logMessage,timestamp from SyslogMessage where facility = \"KERN\"";
        String query7 = "select * from SyslogMessage";
        
//        addQuery(query1);
//        addQuery(query2);
//        addQuery(query3);
//        addQuery(query4);
//        addQuery(query5);
        addQuery(query6);
        addQuery(query7);

//        from("esper://events?configured=true&eql=" + query)
//        .startupOrder(this.startUpOrder + 2)
//        .routeId(this.routeId + "-SELECT")
//        .process(new Processor() {
//                    public void process(Exchange exchange) throws Exception {
//                    	Message in = exchange.getIn();
//                        EventBean event = in.getBody(EventBean.class);
//                        Object e = event.getUnderlying();
//                        in.setBody(e);
//                    }
//                })
//        .marshal().serialization()
//        .to(this.toUri);
    }
    
    private void addQuery(String query) {
        from("esper://events?configured=true&eql=" + query)
        .startupOrder(this.startUpOrder)
        .routeId(this.routeId + "-Query" + this.startUpOrder++)
        .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                    	Message in = exchange.getIn();
                        EventBean event = in.getBody(EventBean.class);
                        Object e = event.getUnderlying();
                        in.setBody(e);
                    }
                })
        .marshal().serialization()
        .to(this.toUri);
    }
}
