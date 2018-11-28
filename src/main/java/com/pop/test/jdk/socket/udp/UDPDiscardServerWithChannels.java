package com.pop.test.jdk.socket.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by pengmaokui on 2018/8/3.
 */
public class UDPDiscardServerWithChannels {
	public final static int PORT = 8080;
	public final static int MAX_PACKET_SIZE = 65507;

	public static void main(String[] args) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		DatagramSocket socket = channel.socket();
		SocketAddress address = new InetSocketAddress(PORT);
		socket.bind(address);

		ByteBuffer buffer = ByteBuffer.allocateDirect(MAX_PACKET_SIZE);
		while(true) {
			SocketAddress client = channel.receive(buffer);
			buffer.flip();
			System.out.println(client + " says ");
			while (buffer.hasRemaining()) System.out.println(buffer.get());
			buffer.clear();
		}
	}
}
