package com.pop.test.jdk.concurrent;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pengmaokui on 2018/6/5.
 */
public class ThreadTest {
	private static final Logger logger = LoggerFactory.getLogger(ThreadTest.class);
	@Test
	public void setUncaughtExceptionHandler() throws InterruptedException {
		Thread t1 = new Thread(() -> {
			for (;;) {
				throw new RuntimeException("soming Exception");
			}
		}, "t1");
		t1.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		t1.start();
		new Thread(() -> {
			for (;;) {
				throw new RuntimeException("soming Exception");
			}
		}, "t2").start();
		Thread.sleep(1000);
	}

	@Test
	public void defaultUncaughtExceptionHandler() throws InterruptedException {
		Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		new Thread(() -> {
			for (;;) {
				throw new RuntimeException("soming Exception");
			}
		}, "t1").start();
		new Thread(() -> {
			for (;;) {
				throw new RuntimeException("soming Exception");
			}
		}, "t2").start();
		Thread.sleep(1000);

	}

	class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
		private Thread.UncaughtExceptionHandler defaultHandler;

		public MyUncaughtExceptionHandler() {
			this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			logger.info("logger println exception info, threadName:{}", t.getName());
		}
	}
}
