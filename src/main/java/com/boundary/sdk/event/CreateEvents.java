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

// Create a whole load of events using many threads

import java.io.DataOutputStream;
//import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

abstract class define {
	
	public static final int THREADS = 30;
	public static final int REPEATS = 20;					// Each thread creates this number of events
	public static final int UNIQUEMULTIPLIER = 100000000 ;  // Every UNIQUEMULTIPLIER event will be de-duped in the thread. if this number is greater than repeats
															// 		then there will be no de-duplication in the run.
	
	public static final int MINIMUMRESPONSE = 500; 			// Milliseconds - rate dampener
		
	public static final boolean DUMMY = false;              // Run with no event creation?
	public static final int MAINTHREADTIMEOUT = 120; 		// Seconds - used as a fail-safe. Main thread will stop all running threads on timeout.
	public static final int COUNTDOWN = 3;
	public static final int THREADMAX = 30;					// Double key on the number of threads to prevent typos
	
    public static final int RANDOMCRASH = -1;           	    // Tests thread resiliency - any number between 0 and 4 will cause the thread to crash with a 1 in 5 probability
    														
}

// Event Pump; pump out lots of events
class EventPump implements Runnable {
   Thread t;
   int myId;
   CountDownLatch myLatch;
   int myRepeats;
   int myMinimumResponse;
   int myCount = 0;
   int myUniqueKey;
   //boolean myUnique;
   boolean myStop = false;
   boolean myDummy = false;
   
   EventPump(int id,CountDownLatch latch, int repeats, int minimumResponse, int uniqueKey, boolean dummy) {
      // Create a new event pump
      t = new Thread(this, "Event Pump");
      //System.out.println(Long.toString(System.currentTimeMillis()) + " Thread # " + t.getId() + "\tCreated.\n");
     
      myLatch = latch;
      myRepeats = repeats;
      myMinimumResponse = minimumResponse;
      myDummy = dummy;
      myId = id;
      myUniqueKey = uniqueKey;
   }
   
   // This is the entry point for the thread
   public void run() {
	   
    while (myCount < myRepeats & myStop == false) {
    	//System.out.println(Long.toString(System.currentTimeMillis()) + "\tThread:\t" + String.format("%1$" + 4 + "s", myId) + " Requesting work " + myRepeats +"\t" + myCount);
         try {
			doWork();
		} catch (Exception e1) {
			//e1.printStackTrace();
	
		 	//System.out.println(Long.toString(System.currentTimeMillis()) + "\tThread:" + String.format("%1$" + 4 + "s", myId) + " Exception recovery from error: " +
		 	//e1.getClass().toString() + " restarting thread at count: " + myCount);
		 	System.err.println(Long.toString(System.currentTimeMillis()) + "\tThread:" + String.format("%1$" + 4 + "s", myId) + " Exception recovery from error: " +
				 	e1.getClass().toString() + " restarting thread at count: " + myCount);
			myCount --; // last count was unsuccessful
		}
   }
    // We have finished - decrement the latch by 1
    try {
    	  myLatch.countDown();
    	 } catch (Exception e) {
    		 e.printStackTrace();
    	 }
   }


private void doWork() throws Exception {

// String url = "https://api.boundary.com/7mKLvFvv2cZBhUpPQYlsg1oKEtC/events";  // 4FtQaHhAFejk0mhb1SJHKx8nzrL

String url = "https://api.boundary.com/4FtQaHhAFejk0mhb1SJHKx8nzrL/events";
int responseCode = 201;

HttpURLConnection con = null;

while (myCount < myRepeats && myStop == false) {
	long now = System.currentTimeMillis();
	
    myCount++;  // persistent across exceptions
    //System.out.println(System.currentTimeMillis() + "\tThread:" + String.format("%1$" + 4 + "s", myId) + " counters: " + myRepeats + " " + myCount);
	
	if (!myDummy ) {

		URL obj = new URL(url);
		//System.out.println(System.currentTimeMillis() + "\tThread:" + String.format("%1$" + 4 + "s", myId) + " Http connection opening");
		con = (HttpURLConnection) obj.openConnection();
		//System.out.println(System.currentTimeMillis() + "\tThread:" + String.format("%1$" + 4 + "s", myId) + " Http connection now open");
       
		
		 
		// Testing code
		if (define.RANDOMCRASH > 0) {
		  Random randomGenerator = new Random();
		  int randomInt = randomGenerator.nextInt(5);
		// System.out.println(System.currentTimeMillis() + "\tThread:" + String.format("%1$" + 4 + "s", myId) + " random: " + randomInt);
		  
		  if (randomInt == 3) {throw new Exception();}
		  
}
		   
        // End of Testing code	
       	 
        

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type" , "application/json" );
		//con.setRequestProperty("Connection", "keep-alive");
		con.setDoOutput(true);

        String urlParameters = "{\"message\": \"Generated from XPS13\",\"tags\": [\"one\", \"two\", \"three\"]," +
			"\"fingerprintFields\": [\"@title\"],\"source\": {\"ref\": \"Rivington\","
			+ "\"type\": \"application\"},\"title\": \"Rivington Test Event " + myUniqueKey + "/" + myId + "/" + myCount%define.UNIQUEMULTIPLIER + "\"}" ;

		//System.out.println(System.currentTimeMillis() + " " + urlParameters);
		// Send post request

		
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		responseCode = con.getResponseCode();

	}

	if (responseCode == 201) {

		long then = System.currentTimeMillis();
		long elapsed = then - now;
		
		System.out.println(System.currentTimeMillis() + "\tThread:" + String.format("%1$" + 4 + "s", myId) + 
				" New event\t"  + myUniqueKey + "/" + myId + "/" + myCount%define.UNIQUEMULTIPLIER + "\t" + Long.toString(elapsed) + "\t(Ms) ") ;

		if (elapsed < myMinimumResponse) {
			try {
			    Thread.sleep(myMinimumResponse-elapsed);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}
	else {
		System.out.println(System.currentTimeMillis() + "\tThread:" + String.format("%1$" + 4 + "s", myId) + 
				" Not the response code (201) that we wanted: " + responseCode);
		if (responseCode == 429) {Thread.sleep(100);} // Server too busy so we should wait a short while
		//System.exit(responseCode);  /* Bad response code - give up */
		
		myCount--; // And reset the persistent record counter
	}


}

	if (myStop) {
	   System.out.println(System.currentTimeMillis() + "\tThread:" + String.format("%1$" + 4 + "s", myId) + 
				" Terminated with STOP request");
	}
	else {
	   System.out.println(System.currentTimeMillis() + "\tThread:" + String.format("%1$" + 4 + "s", myId) + 
				" ended - " + myRepeats + " events created");
	}


}   

}

public class CreateEvents {
	
	
public static void main(String args[]) throws InterruptedException {
		  
	int threads = define.THREADS;
	int repeats = define.REPEATS;
	int minimumResponse = define.MINIMUMRESPONSE; 		// Milliseconds - used to slow down the threads pumping rate
	int mainThreadTimeout = define.MAINTHREADTIMEOUT; 	// Seconds
	boolean dummy = define.DUMMY;                       // Do everything but create an event
	   
	  Authenticator.setDefault(new CustomAuthenticator());
	  
	  for (int i=0;i<args.length;i++) {
		  System.out.println(args[i]);
		  // TODO: Able to compile on Java 6
//		  switch(args[i]) {
//		  	case "-t": {
//		  		threads = Integer.parseInt(  args[i+1]);
// 		  	}  
//		  	case "-r": {
//		  		repeats = Integer.parseInt(  args[i+1]);
// 		  	} 
//		  	case "-m": {
//		  		minimumResponse = Integer.parseInt(  args[i+1]);
// 		  	} 
//		  	case "-w": {
//		  		mainThreadTimeout = Integer.parseInt(  args[i+1]);
// 		  	} 
//  		
// 		  	
//		  }
	  }
	  
	  if (threads > define.THREADMAX) {threads = define.THREADMAX;}
	  
	  CountDownLatch latch = new CountDownLatch(threads);

	  boolean latchState = true;
	  

	  System.out.println(System.currentTimeMillis() + "\tMain thread started and creating " + threads + " threads " 
			  + repeats + " repeats " + minimumResponse + " (Ms) minimum response. Main Thread Timeout " + mainThreadTimeout);
	      
	      EventPump[] threadList = new EventPump[threads];
	      int uniqueKey = (int) System.currentTimeMillis()/1000 ;
	      
		  for (int i = 0; i < threads; i++) { 
			  threadList[i] = new EventPump(i,latch,repeats,minimumResponse,uniqueKey,dummy); // create a new thread but do not start
		  }
		  

		  for (int i=define.COUNTDOWN; i>0; i--) {
			  System.out.println(System.currentTimeMillis() + "\tMain thread countdown: " + i);
			  Thread.sleep(1000);
		  }
		  long now = System.currentTimeMillis();
		  
		  for (int i = 0; i < threads; i++) { 
			  threadList[i].t.start(); // Start your engines!
		  }
		  System.out.println(System.currentTimeMillis() + "\tMain thread - all threads started. Waiting on latch.");
		  
		  try {
			 latchState = latch.await(mainThreadTimeout, TimeUnit.SECONDS);
		
		  } catch (InterruptedException e) {
			  e.printStackTrace();
		  }
	 
      long then = System.currentTimeMillis();
      if (latchState) {
    	  System.out.println(then + "\tMain thread exiting. Run time for " + threads + " threads " + 
    			  repeats + " repeats: " + Long.toString(then-now) + " milliseconds.");
      }
      else {
    	  System.out.println(then + "\tMain thread timed out waiting for child threads. " + Long.toString(then-now) + " milliseconds.");
          for (int i=0; i < threads; i++) {
        	  if (threadList[i].t.isAlive()) {
           		  System.out.println(Long.toString(System.currentTimeMillis()) + " Closing down thread: " + i);
        		  threadList[i].myStop = true;
        		  //threadList[i].t.interrupt();
          	  }
          }
          for (int i=0; i < threads;i++){
      		  threadList[i].t.join();   // Make sure that all child threads stopped before stopping main thread
          }
          System.out.println(Long.toString(then) + "\tMain thread terminating after timeout. " + Long.toString(then-now) + " milliseconds.");
          
      }
   }



public static class CustomAuthenticator extends Authenticator { 
    // Called when password authorisation is needed 
    protected PasswordAuthentication getPasswordAuthentication() { 

   // Get information about the request 
      //String prompt = getRequestingPrompt(); 
      //String hostname = getRequestingHost(); 
      //InetAddress ipaddr = getRequestingSite(); 
      //int port = getRequestingPort(); 
      //System.out.println(System.currentTimeMillis() + "\tAuthentication requestor: "+ prompt +" " + hostname + " " + port);
      String username = "E222tY6py4e9InofIEZOWkrjHDL"; 
      String password = ""; 
      
      // Return the information (a data holder that is used by Authenticator) 
      return new PasswordAuthentication(username, password.toCharArray()); 
   } 

}
}