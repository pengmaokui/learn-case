package com.pop.test.jdk.concurrent.lock;

/**
 * Created by pengmaokui on 2018/3/5.
 */
public class LockTestObject {
	private String value;

	public synchronized String getValue() {
		System.out.println(System.currentTimeMillis() + "begin... get value. thread:" + Thread.currentThread().getName());
		return value;
	}

	public synchronized void setValue(String value) {
		System.out.println(System.currentTimeMillis() + "begin... set value. thread:" + Thread.currentThread().getName());
		this.value = value;
		try {
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end... set value. thread:" + Thread.currentThread().getName());
	}

	public void testThis(String value) {
		synchronized (this) {
			System.out.println(System.currentTimeMillis() + "begin... set value. thread:" + Thread.currentThread().getName());
			this.value = value;
			try {
				Thread.sleep(1000 * 5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("end... set value. thread:" + Thread.currentThread().getName());
		}
	}
}
