package com.boundary.sdk;

import java.io.*;
import java.net.*;

class UDPServer {
	public static void main(String args[]) throws Exception {
		if (args.length != 1) {
			System.out.println("useage: port");
		}
		int serverPort = Integer.parseInt(args[0]);
		DatagramSocket serverSocket = new DatagramSocket(serverPort);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			System.out.println("RECEIVED: " + sentence);
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			String capitalizedSentence = sentence.toUpperCase();
			sendData = capitalizedSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}
}