package com.boundary.sdk;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BoundarySpringSDKApp {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("META-INF/boundary-sdk-routes.xml");
        applicationContext.start();

        // let the Camel runtime do its job for 5 seconds
        Thread.sleep(5000);

        // shutdown
        applicationContext.stop();
    }
}
