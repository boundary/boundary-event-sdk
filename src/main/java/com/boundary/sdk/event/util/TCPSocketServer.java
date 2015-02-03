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
package com.boundary.sdk.event.util;

import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Date;

/**
 * 1) Listens on the configured port.
 * 2) When client connects send back the current time
 */
public class TCPSocketServer {

    /**
     * Runs the server.
     * @param args Command line parameters
     * @throws IOException IO error occurs
     */
    public static void main(String[] args) throws IOException{
    	
    	if (args.length != 1) {
    		System.out.println("usage: <port>");
    		System.exit(1);
    	}
    	int port = Integer.parseInt(args[0]);
        ServerSocket listener = new ServerSocket(port);
        listener.setReuseAddress(true);
        try {
        	System.out.println("Listening on TCP port " + port);
            while (true) {

                Socket socket = listener.accept();
                try {
                	System.out.println("Open connection from client " + socket.getInetAddress());
                    PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date().toString());
                } finally {
                	System.out.println("Close connection from client " + socket.getInetAddress());
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
}
