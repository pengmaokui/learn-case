package com.pop.test.jdk.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by pengmaokui on 2018/8/1.
 */
public class NonblockingSingleFileHTTPServer {
	private ByteBuffer contentBuffer;
	private int port = 8080;

	public NonblockingSingleFileHTTPServer(ByteBuffer data, String encoding, String MIMEType, int port) {
		this.port = port;
		String header = "HTTP/1.0 200 OK\r\n" +
				"Server: OneFile 2.0\r\n" +
				"Content-length: " + data.limit() + "\r\n" +
				"Content-type: " + MIMEType + "; charset=" + encoding + "\r\n";
		byte[] headerData = header.getBytes(Charset.forName("US-ASCII"));

		ByteBuffer buffer = ByteBuffer.allocate(data.limit() + headerData.length);
		buffer.put(headerData);
		buffer.put(data);
		buffer.flip();
		this.contentBuffer = buffer;
	}

	public void run() throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		ServerSocket ss = serverSocketChannel.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		ss.bind(address);
		serverSocketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

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
						client.register(selector, SelectionKey.OP_READ);
					} else if (key.isWritable()) {
						SocketChannel channel = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if (buffer.hasRemaining()) {
							channel.write(buffer);
						} else {
							channel.close();
						}
					} else if (key.isReadable()) {
//						SocketChannel channel = (SocketChannel) key.channel();
//						ByteBuffer buffer = ByteBuffer.allocate(4096);
//						channel.read(buffer);

						//将通道切换为只写模式
						key.interestOps(SelectionKey.OP_WRITE);
						key.attach(contentBuffer.duplicate());
					}
				} catch (IOException ex) {
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

	public static void main(String[] args) throws IOException {
		String fileName = "/Users/pengmaokui/qa_payplus_customer_category.sql";
		String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
		Path file = FileSystems.getDefault().getPath(fileName);
		byte[] data = Files.readAllBytes(file);
		ByteBuffer input = ByteBuffer.wrap(data);

		String encoding = "UTF-8";

		NonblockingSingleFileHTTPServer nonblockingSingleFileHTTPServer =
				new NonblockingSingleFileHTTPServer(input, encoding, contentType, 8090);
		nonblockingSingleFileHTTPServer.run();
	}
}
