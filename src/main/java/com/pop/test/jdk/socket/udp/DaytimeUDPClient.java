package com.pop.test.jdk.socket.udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by pengmaokui on 2018/8/3.
 */
public class DaytimeUDPClient {
	private final static int PORT = 8080;

	private static final String HOSTNAME = "time.nist.gov";

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			socket.setSoTimeout(1000 * 10);
			InetAddress host = InetAddress.getByName(HOSTNAME);
			DatagramPacket request = new DatagramPacket(new byte[1], 1, host, PORT);
			DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
			socket.send(request);
			socket.receive(response);
			String result = new String(response.getData(), 0, response.getLength(), "US-ASCII");
			System.out.println(result);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
