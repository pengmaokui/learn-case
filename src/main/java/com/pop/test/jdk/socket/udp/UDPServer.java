package com.pop.test.jdk.socket.udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pengmaokui on 2018/8/3.
 */
public abstract class UDPServer implements Runnable {

	private final int bufferSize;
	private final int port;
	private final Logger logger = LoggerFactory.getLogger(UDPServer.class);
	private volatile boolean isShutDown = false;

	public UDPServer(int bufferSize, int port) {
		this.bufferSize = bufferSize;
		this.port = port;
	}

	public UDPServer(int port) {
		this(port, 8192);
	}

	@Override
	public void run() {
		byte[] buffer = new byte[bufferSize];
		try (DatagramSocket socket = new DatagramSocket(port)) {
			socket.setSoTimeout(1000 * 10);
			while(true) {
				if (isShutDown) return;
				DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
				try {
					socket.receive(incoming);
					this.respond(socket, incoming);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public abstract void respond(DatagramSocket socket, DatagramPacket request) throws IOException;

	public void shutDown() {
		this.isShutDown = true;
	}
}
