package com.pop.test.jdk.concurrent.volatiletest;

/**
 * Created by pengmaokui on 2017/12/6.
 */
public class VolatileVisibiltyTest {
	private volatile boolean flag;

	public void test() {
		while (!flag) {
			//doSomething();
		}
		System.out.println("退出线程");
	}

	public static void main(String[] args) throws InterruptedException {
		VolatileVisibiltyTest test = new VolatileVisibiltyTest();
		new Thread(() -> {
			test.test();
		}).start();

		Thread.sleep(1000);

		new Thread(() -> {
			test.flag = true;
		}).start();
	}
}
