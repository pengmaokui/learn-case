package com.pop.test.jdk.socket.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by pengmaokui on 2018/8/4.
 */
public class UDPEchoClientWithChannels {
	public final static int PORT = 8080;
	public final static int LIMIT = 100;
	public final static String address = "127.0.0.1";

	public static void main(String[] args) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		channel.configureBlocking(false);
		SocketAddress remote = new InetSocketAddress(address, PORT);
		channel.connect(remote);

		Selector selector = Selector.open();
		channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

		ByteBuffer buffer = ByteBuffer.allocate(4);
		int n = 0;
		int numbersRead = 0;
		while (true) {
			if (numbersRead == LIMIT) break;
			// 为一个连接等待一分钟
			selector.select(60 * 1000);
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			if (readyKeys.isEmpty() && n == LIMIT) {
				// 所有包已写入，看起来
				// 好像不会再有更多数据从网络代码
				break;
			} else {
				Iterator<SelectionKey> iterator = readyKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					iterator.remove();
					if (key.isReadable()) {
						buffer.clear();
						channel.read(buffer);
						buffer.flip();
						int echo = buffer.getInt();
						System.out.println("Read: " + echo);
						numbersRead++;
					}

					if (key.isWritable()) {
						buffer.clear();
						buffer.putInt(n);
						buffer.flip();
						channel.write(buffer);
						System.out.println("Write:" + n);
						n++;
						if (n == LIMIT) {
							//所有包已写入，切换到只读模式
							key.interestOps(SelectionKey.OP_READ);
						}
					}
				}
			}
		}

		System.out.println("Echoed " + numbersRead + " out of " + LIMIT + " sent");
		System.out.println("Success rate: " + 100.0 * numbersRead / LIMIT + "%");
	}
}
