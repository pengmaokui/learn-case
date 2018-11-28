package com.pop.test.jdk.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by pengmaokui on 2018/8/5.
 */
public class MulticastSniffer {

	public static void main(String[] args) throws UnknownHostException {
		InetAddress group = InetAddress.getByName("239.255.255.250");
		int port = 1900;

		MulticastSocket ms = null;
		try {
			ms = new MulticastSocket(port);
			ms.joinGroup(group);

			byte[] buffer = new byte[8192];
			while(true) {
				DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
				ms.receive(dp);
				String s = new String(dp.getData(), "8859_1");
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
