package com.pop.test.jdk.concurrent;

import org.junit.Test;

public class JoinTest {

	/**
	 * 为了模拟join得实现
	 * 测试方法:
	 * 	1.开启线程
	 * 	2.使用线程进行wait() 永久等待
	 * 	3.线程结束主线程自动被唤醒
	 * 	4.换成Object.wait()则主线程不会被唤醒
	 * 测试结果:
	 * 	1.线程结束会调用线程对象的thread.notifyAll()方法;
	 * 	2.其他的对象wait并不会被唤醒
	 * @throws InterruptedException
	 */
	@Test
	public void simulationJoin() throws InterruptedException {
		Thread thread = new Thread(() -> {
			System.out.println("start test");
		});
		thread.start();

		//thread结束子线程自动唤醒
		synchronized (thread) {
			thread.wait();
		}
		//线程不会被唤醒
//		Object o = new Object();
//		synchronized (o) {
//			o.wait();
//		}
		System.out.println("end test");
	}
}
