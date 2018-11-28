package com.pop.test.jdk.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by pengmaokui on 2018/7/31.
 */
public class IntgenClient {
	public static int DEFAULT_PORT = 8092;

	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		String ip = "127.0.0.1";

		try {
			SocketAddress address = new InetSocketAddress(ip, port);
			SocketChannel client = SocketChannel.open(address);
			ByteBuffer buffer = ByteBuffer.allocate(4);
			IntBuffer view = buffer.asIntBuffer();

			for (int expected = 0;; expected++) {
				client.read(buffer);
				int actual = view.get();
				buffer.clear();
				view.rewind();

				if (actual != expected) {
					System.err.println("Expected " + expected + "; was " + actual);
					break;
				}
				System.out.println(actual);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
