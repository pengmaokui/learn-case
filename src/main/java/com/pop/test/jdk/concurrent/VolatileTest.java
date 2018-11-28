package com.pop.test.jdk.concurrent;

/**
 * Created by pengmaokui on 2017/11/23.
 */
public class VolatileTest {
	private volatile int value = 0;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			VolatileTest volatileTest = new VolatileTest();
			Thread thread1 = new Thread(() -> System.out.println(volatileTest.getValue()));
			Thread thread2 = new Thread(() -> volatileTest.setValue(1));
			thread1.start();
			thread2.start();
		}
	}
}
