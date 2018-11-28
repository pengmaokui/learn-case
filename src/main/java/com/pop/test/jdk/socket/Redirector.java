package com.pop.test.jdk.socket;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * Created by pengmaokui on 2018/7/24.
 */
public class Redirector {
	private static final Logger logger = LoggerFactory.getLogger(Redirector.class);

	private final int port;

	private final String newSite;

	public Redirector(int port, String newSite) {
		this.port = port;
		this.newSite = newSite;
	}

	public void start() {
		try (ServerSocket server = new ServerSocket(port)) {
			logger.info("Redirecting connections on port "
					+ server.getLocalPort() + " to " + newSite);

			while (true) {
				try {
					Socket s = server.accept();
					Thread t = new Thread(new RedirectThread(s));
					t.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class RedirectThread implements Runnable {

		private final Socket connection;

		private RedirectThread(Socket connection) {
			this.connection = connection;
		}

		@Override
		public void run() {
			try {
				Writer out = new BufferedWriter(
						new OutputStreamWriter(connection.getOutputStream(), "US-ASCII"));
				Reader in = new InputStreamReader(
						new BufferedInputStream(connection.getInputStream()));
				//只读取第一行，这是我们需要的全部内容
				StringBuilder request = new StringBuilder(80);
				while (true) {
					int c = in.read();
					if (c == '\r' || c == '\n' || c == -1) break;
					request.append((char) c);
				}

				String get = request.toString();
				String[] pieces = get.split(" ");
				String theFile = pieces[1];

				// 如果是HTTP/1.0或以后版本，则发送一个MIME首部
				if (get.contains("HTTP")) {
					out.write("HTTP/1.0 302 FOUND\r\n");
					Date now = new Date();
					out.write("Date: " + now + "\r\n");
					out.write("Server: Redirector 1.1\r\n");
					out.write("Location: " + newSite + theFile + "\r\n");
					out.write("Content-type: text/html\r\n\r\n");
					out.flush();
				}
				//并不是所有浏览器都支持重定向,所以我们需要
				//生成html指出文档转移到哪里
				out.write("<HTML><HEAD><TITLE>Document moved</TITLE></HEAD>\r\n");
				out.write("<BODY><H1>Document moved</H1>\r\n");
				out.write("The document " + theFile +
						" has moved to\r\n<a href = \"" + newSite + theFile + "\">" +
						newSite + theFile + "<a>.\r\n Please update your bookmarks<P>");
				out.write("</BODY></HTML>\r\n");
				out.flush();
				logger.info("Redirected" + connection.getRemoteSocketAddress());
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
//		int thePort = 8080;
//		String theSite = "https://baidu.com";
//		Redirector redirector = new Redirector(thePort, theSite);
//		redirector.start();

		System.out.println(JSON.toJSONString("HTTP html".split(" ")));
	}
}
