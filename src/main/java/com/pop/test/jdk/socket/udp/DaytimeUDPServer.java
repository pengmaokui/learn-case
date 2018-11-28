package com.pop.test.jdk.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pengmaokui on 2018/8/3.
 */
public class DaytimeUDPServer {
	private static final int PORT = 8080;

	private static final Logger logger = LoggerFactory.getLogger(DaytimeUDPServer.class);

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			while (true) {
				try {
					DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
					socket.receive(request);

					String daytime = new Date().toString();
					byte[] data = daytime.getBytes("US-ASCII");
					DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
					socket.send(response);
					logger.info(daytime + " " + request.getAddress());
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
