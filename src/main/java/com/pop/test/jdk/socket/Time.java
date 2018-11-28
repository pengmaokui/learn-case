package com.pop.test.jdk.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by pengmaokui on 2018/7/23.
 */
public class Time {

	private static final String HSOTNAME = "time.nist.gov";

	public static void main(String[] args) {
		try (Socket socket = new Socket(HSOTNAME, 37)) {
			socket.setSoTimeout(15 * 1000);

			InputStream inputStream = socket.getInputStream();
			long l = 0;
			for (int i = 0 ; i < 4; i++) {
				l = inputStream.read()|(l << 8);
			}
			System.out.println(l);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
