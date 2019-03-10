package com.pop.test.jdk.concurrent;

import org.junit.Test;

public class ThreadTest {

	/**
	 * 一个守护线程开启一个非守护线程一样会被结束
	 * test启动的线程就是守护线程
	 */
	@Test
	public void daemonThread() {
		Thread currentThread = Thread.currentThread();
		System.out.println(currentThread.getThreadGroup());
		Thread thread = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
}
