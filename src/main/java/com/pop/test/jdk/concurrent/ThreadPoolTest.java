package com.pop.test.jdk.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * Created by pengmaokui on 2017/2/22.
 */
public class ThreadPoolTest {
	@Test
	public void testParam() {
		int COUNT_BITS = Integer.SIZE - 3;
		int CAPACITY   = (1 << COUNT_BITS) - 1;
		int RUNNING    = -1 << COUNT_BITS;
		int SHUTDOWN   =  0 << COUNT_BITS;
		int STOP       =  1 << COUNT_BITS;
		int TIDYING    =  2 << COUNT_BITS;
		int TERMINATED =  3 << COUNT_BITS;
		System.out.println(Integer.toBinaryString(1));
		System.out.println(Integer.toBinaryString(CAPACITY));
		System.out.println(Integer.toBinaryString(RUNNING | 1));
		System.out.println(Integer.toBinaryString((RUNNING | 1) & CAPACITY));
	}

	@Test
	public void testWorkZero() {
		MyThreadPoolExecutor executor = new MyThreadPoolExecutor(1, 1, 1,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1), new ThreadFactory() {
			private int count = 0;
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, ++count + "");
			}
		});
		executor.allowCoreThreadTimeOut(true);
		for (int i = 0; i < 10000; i++) {
			int finalI = i;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			executor.execute(() -> {
			});

		}
	}

	@Test
	public void uncaughtExceptionTest() {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		try {
			executorService.submit(() -> {
				Object obj = null;
				System.out.println(obj.toString());
			});
		} catch (Exception e) {
			System.out.println("catch Exception");
			e.printStackTrace();
		}
		try {
			executorService.execute(() -> {
				Object obj = null;
				System.out.println(obj.toString());
			});
		} catch (Exception e) {
			System.out.println("catch Exception");
			e.printStackTrace();
		}
	}

	@Test
	public void caughtSumbitException() {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Future future = executorService.submit(() -> {
			Object obj = null;
			System.out.println(obj.toString());
		});
		try {
			future.get();
		} catch (Exception e) {
			System.out.println("catch NullPointException");
		}
	}

}
