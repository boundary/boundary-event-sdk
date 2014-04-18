package com.boundary.sdk;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;


/**
 * Builds are route that is Queue using ActiveMQ
 * 
 * @author davidg
 *
 */
public class QueueRouterBuilder extends BoundaryRouteBuilder {

	public QueueRouterBuilder() {
		// TODO Auto-generated constructor stub
	}

    @Override
    public void configure() {
    	
    	CamelContext  context = getContext();
    	        // connect to embedded ActiveMQ JMS broker
        ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory("vm://localhost");
        context.addComponent("jms",
            JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        // load file orders from src/data into the JMS queue
        from("direct:syslog-queue")
        .to("jms:incomingOrders")
        .to(toUri);

//        // content-based router
//        from("jms:incomingOrders")
//        .choice()
//            .when(header("CamelFileName").endsWith(".xml"))
//                .to("jms:topic:xmlOrders")  
//            .when(header("CamelFileName").endsWith(".csv"))
//                .to("jms:topic:csvOrders");
//
//        from("jms:topic:xmlOrders").to("jms:accounting");  
//        from("jms:topic:xmlOrders").to("jms:production");  
//        
//        // test that our route is working
//        from("jms:accounting").process(new Processor() {
//            public void process(Exchange exchange) throws Exception {
//                System.out.println("Accounting received order: "
//                        + exchange.getIn().getHeader("CamelFileName"));  
//            }
//        });
//        from("jms:production").process(new Processor() {
//            public void process(Exchange exchange) throws Exception {
//                System.out.println("Production received order: "
//                        + exchange.getIn().getHeader("CamelFileName"));  
//            }
//        });
    }    
}
