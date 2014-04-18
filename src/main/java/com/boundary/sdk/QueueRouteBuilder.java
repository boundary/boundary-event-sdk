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
public class QueueRouteBuilder extends BoundaryRouteBuilder {
	
	private String queueName;
	
	private static final String DEFAULT_QUEUE_NAME="direct:queue";

	public QueueRouteBuilder() {
		queueName = DEFAULT_QUEUE_NAME;
	}
	
	/**
	 * Sets the name of JMS queue
	 * 
	 * @param queueName
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	/**
	 * Gets the name of the JMS queue
	 * 
	 * @return
	 */
	public String getQueueName() {
		return this.queueName;
	}
	

    @Override
    public void configure() {
    	
    	CamelContext  context = getContext();
    	        // connect to embedded ActiveMQ JMS broker
        ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory("vm://localhost");
        context.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        // load file orders from src/data into the JMS queue
        from(fromUri)
        .routeId(routeId)
        .to("jms:pending_events?asyncConsumer=true")
        .to("log:com.boundary.sdk.QueueRouterBuilder?level=INFO&groupInterval=10000&groupDelay=60000&groupActiveOnly=false")
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
