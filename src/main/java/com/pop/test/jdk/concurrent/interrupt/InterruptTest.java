package com.pop.test.jdk.concurrent.interrupt;

import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

/**
 * Created by pengmaokui on 2018/6/1.
 */
public class InterruptTest {
	/**
	 * 线程被打断了会执行finally
	 * @throws InterruptedException
	 */
	@Test
	public void interruptExecuteFinally() throws InterruptedException {
		Thread thread = new Thread(() -> {
			try {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println();
			} finally {
				System.out.println("finally is executed");
			}
		});
		thread.start();

		thread.interrupt();
		Thread.sleep(1000);
	}

	/**
	 * interrupt()时,线程不是sleep(),wait(),part()就不会throw InterruptException()
	 * 只是把线程得状态改成了中断,并不会影响线程得正常执行,sleep(),wait(),part()会检查线程得状态,
	 * 然后才会抛出中断异常
	 * @throws InterruptedException
	 */
	@Test
	public void interruptExecuteContinue() throws InterruptedException {
		Thread thread = new Thread(() -> {
			try {
				for (int i= 0; i< 10; i++) {
					System.out.println("i:" + i);
				}
			} finally {
				System.out.println("finally is executed");
			}
		});
		thread.start();
		thread.interrupt();
		System.out.println("thread is interrupt:" + thread.isInterrupted());
		Thread.sleep(1000);
	}

	@Test
	public void parkTest() throws InterruptedException {
		Thread thread = new Thread(() -> {
			LockSupport.park(this);
			System.out.println("thread isInterrupted:" + Thread.interrupted());
		});

		thread.start();
		Thread.sleep(1000 * 1);

		thread.interrupt();
	}
}
