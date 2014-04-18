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

        /**
         * Receives serialized @{link RawEvent} messages
         */
        from(fromUri)
        .routeId(routeId)
        .to("jms:pending_events?asyncConsumer=true")
		.to("log:com.boundary.sdk.QueueRouteBuilder?level=DEBUG&showHeaders=true&multiline=true")
        .to(toUri)
        ;
    }    
}
