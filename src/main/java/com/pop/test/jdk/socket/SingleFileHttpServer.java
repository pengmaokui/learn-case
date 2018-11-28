package com.pop.test.jdk.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pengmaokui on 2018/7/24.
 */
public class SingleFileHttpServer {
	private static final Logger logger = LoggerFactory.getLogger(SingleFileHttpServer.class);

	private final byte[] content;
	private final byte[] header;
	private final int port;
	private final String encoding;

	public SingleFileHttpServer(byte[] data, String encoding, String mimeType, int port) {
		this.content = data;
		this.port = port;
		this.encoding = encoding;
		String header = "HTTP/1.0 200 OK\r\n" +
				"Server: OneFile 2.0\r\n" +
				"Content-length: " + this.content.length + "\r\n" +
				"Content-type: " + mimeType + "; charset=" + encoding + "\r\n";
		this.header = header.getBytes(Charset.forName("US-ASCII"));
	}

	public void start() {
		ExecutorService pool = Executors.newFixedThreadPool(100);
		try (ServerSocket server = new ServerSocket(this.port)) {
			logger.info("Accepting connections on port " + server.getLocalPort());
			logger.info("Data to be sent:");
			logger.info(new String(this.content, encoding));
			while (true) {
				Socket connection = server.accept();
				pool.submit(new HTTPHandler(connection));
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	private class HTTPHandler implements Runnable {

		private final Socket connection;

		public HTTPHandler(Socket connection) {
			this.connection = connection;
		}

		@Override
		public void run() {
			try {
				OutputStream out = new BufferedOutputStream(connection.getOutputStream());
				InputStream in = new BufferedInputStream(connection.getInputStream());
				//只读取第一行，这是我们需要的全部内容
				StringBuilder request = new StringBuilder(80);
				while (true) {
					int c = in.read();
					if (c == '\r' || c == '\n' || c == -1) break;
					request.append(c);
				}
				// 如果是HTTP/1.0或以后版本，则发送一个MIME首部
				if (request.toString().contains("HTTP/")) {
					out.write(header);
				}
				out.write(content);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		int port = 80;
		String encoding = "UTF-8";
		String filePath = "";

		try {
			Path path = Paths.get(filePath);
			byte[] data = Files.readAllBytes(path);

			String contentType = URLConnection.getFileNameMap().getContentTypeFor(filePath);
			SingleFileHttpServer server = new SingleFileHttpServer(data, encoding, contentType, port);
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
