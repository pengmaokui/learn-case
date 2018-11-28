package com.pop.test.jdk.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

/**
 * Created by pengmaokui on 2018/7/25.
 */
public class ChargenServer {
	public static int DEFAULT_PORT = 8019;

	public static void main(String[] args) {
		int port = DEFAULT_PORT;

		System.out.println("Listening for connections on port " + port);

		byte[] rotation = new byte[95 * 2];
		for (byte i = ' '; i <= '~'; i++) {
			rotation[i - ' '] = i;
			rotation[i + 95 - ' '] = i;
		}

		ServerSocketChannel serverSocketChannel;
		Selector selector;
		try {
			serverSocketChannel = ServerSocketChannel.open();
			ServerSocket ss = serverSocketChannel.socket();
			InetSocketAddress address = new InetSocketAddress(port);
			ss.bind(address);
			serverSocketChannel.configureBlocking(false);
			selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (true) {
			try {
				selector.select();
			} catch (IOException ex) {
				ex.printStackTrace();
				break;
			}

			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				try {
					if (key.isAcceptable()) {
						ServerSocketChannel server = (ServerSocketChannel) key.channel();
						SocketChannel client = server.accept();
						System.out.println("Accepted connection from " + client);
						client.configureBlocking(false);
						SelectionKey clientKey = client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
						ByteBuffer buffer = ByteBuffer.allocate(74);
						buffer.put(rotation, 0, 72);
						buffer.put((byte) '\r');
						buffer.put((byte) '\n');
						buffer.flip();
						clientKey.attach(buffer);
					} else if (key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if (!buffer.hasRemaining()) {
							// 用下一行重新填充缓冲区
							buffer.rewind();
							// 得到上一次的首字符
							int first = buffer.get();
							// 准备改变缓冲区中的数据
							buffer.rewind();
							// 寻找rotation中细腻的首字符未知
							int position = first - ' ' + 1;
							// 将数据从rotation复制到缓存区
							buffer.put(rotation, position, 72);
							// 在缓冲区末尾存储一个分割符
							buffer.put((byte) '\r');
							buffer.put((byte) '\n');
							buffer.flip();
						}
						client.write(buffer);
					}
				} catch (ClosedChannelException e) {
					e.printStackTrace();
				} catch (IOException e) {
					key.cancel();
					try {
						key.channel().close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	@Test
	public void test() {
		System.out.println((int) '~');
		System.out.println((int) ' ');
	}
}
