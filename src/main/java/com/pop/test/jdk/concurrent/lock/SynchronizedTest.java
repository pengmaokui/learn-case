package com.pop.test.jdk.concurrent.lock;

import org.junit.Test;

/**
 * Created by pengmaokui on 2018/3/5.
 */
public class SynchronizedTest {

	/**
	 * 结论 method的sync对象是new Object()
	 * @throws InterruptedException
	 */
	@Test
	public void testMethod() throws InterruptedException {
		LockTestObject o1 = new LockTestObject();
		LockTestObject o2 = new LockTestObject();

		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				o1.setValue("1");
			}).start();
			new Thread(() ->{
				o2.setValue("1");
			}).start();
		}

		Thread.sleep(1000 * 200);
	}

	/**
	 * 结论 this的sync对象是new Object()
	 * @throws InterruptedException
	 */
	@Test
	public void testThis() throws InterruptedException {
		LockTestObject o1 = new LockTestObject();
		LockTestObject o2 = new LockTestObject();

		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				o1.testThis("1");
			}).start();
			new Thread(() ->{
				o2.testThis("1");
			}).start();
		}

		Thread.sleep(1000 * 200);
	}
}
