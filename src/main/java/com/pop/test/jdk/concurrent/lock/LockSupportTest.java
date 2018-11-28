package com.pop.test.jdk.concurrent.lock;

import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

/**
 * Created by pengmaokui on 2018/5/4.
 */
public class LockSupportTest {
	@Test
	public void testLockBlock() {
		LockSupport.park(this);
		System.out.println("park block,this msg not print");
	}

	@Test
	public void testInterrupt() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					LockSupport.park(this);
					System.out.println("park block,this msg not print");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		Thread.sleep(1000);
		thread.interrupt();
		System.out.println("thread interrupt, park not throw InterruptException");
		//park 只能在判断一次然后抛出InterruptException

		Object o = new Object();
		Thread waitThread = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (o) {
					try {
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		waitThread.start();

		waitThread.interrupt();
		System.out.println("waitThread interrupt, wait throw InterruptException");
	}
}
