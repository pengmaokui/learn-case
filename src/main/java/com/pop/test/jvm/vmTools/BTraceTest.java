package com.pop.test.jvm.vmTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by pengmaokui on 2017/4/4.
 */
public class BTraceTest {
	private int add(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) throws IOException {
		BTraceTest traceTest = new BTraceTest();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 0; i < 10; i++) {
			reader.readLine();
			int a = (int) Math.round(Math.random() * 10000);
			int b = (int) Math.round(Math.random() * 10000);
			System.out.println(traceTest.add(a, b));
		}
	}
}
