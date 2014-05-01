package com.boundary.sdk.event;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.daemon.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EchoTask extends TimerTask {
    @Override
    public void run() {
        System.out.println(new Date() + " running ...");
    }
}

public class BoundaryEventDaemon extends EventApplication implements Daemon {
	
	private static Logger LOG = LoggerFactory.getLogger(BoundaryEventDaemon.class);

    private static Timer timer = null;
    
    public BoundaryEventDaemon() {
    	
    }

    public static void main(String[] args) {
//        timer = new Timer();
//        timer.schedule(new EchoTask(), 0, 1000);
    }

    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        System.out.println("initializing ...");
    }

    public void start() throws Exception {
        System.out.println("starting ...");
        boot();
    }

    public void stop() throws Exception {
        System.out.println("stopping ...");
        main.stop();
        
        if (timer != null) {
            timer.cancel();
        }
    }

    public void destroy() {
        System.out.println("done.");
    }
 }

