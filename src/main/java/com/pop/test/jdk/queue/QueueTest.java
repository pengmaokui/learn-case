package com.pop.test.jdk.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * Created by pengmaokui on 2018/3/7.
 */
public class QueueTest {

	/**
	 * 测试简单的阻塞队列 利用object.wait和object.notifyAll
	 */
	@Test
	public void SimpleTest() throws InterruptedException {
		SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue(10);
		for (int i = 0; i < 11; i ++) {
			int finalI = i;
			new Thread(()-> {
				try {
					simpleBlockingQueue.put(finalI);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}

		Thread.sleep(100);

		for (int i = 0; i < 11; i ++) {
			new Thread(()-> {
				try {
					System.out.println(simpleBlockingQueue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}

		Thread.sleep(100);
	}

	@Test
	public void MoreProductSignalCustomerBlockingQueue() throws InterruptedException {
		MoreProductSignalCustomerBlockingQueue<Integer> blockingQueue = new MoreProductSignalCustomerBlockingQueue(10);
		AtomicInteger productInt = new AtomicInteger();
		for (int i = 0; i < 1000; i++) {
			int finalI = i;
			new Thread(() -> {
				try {
					blockingQueue.put(finalI);
					productInt.addAndGet(finalI);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
		Thread.sleep(100);

		AtomicInteger consumerInt = new AtomicInteger();
		for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				try {
					int j = blockingQueue.take();
					consumerInt.addAndGet(j);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}

		Thread.sleep(1000 * 5);
		System.out.println("productInt:" + productInt.get() + ",consumerInt:" + consumerInt.get());
	}

	@Test
	public void LikedBlockQueueTest() throws InterruptedException {
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(4);
		for (int i = 0; i < 7; i++) {
			int finalI = i;
			new Thread(() -> {
				try {
					queue.put(finalI);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
		Thread.sleep(1000 );

		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
		System.out.println(queue.take());
		Thread.sleep(1000 * 60 * 60);

	}

	public static void main(String[] args) {
		MoreProductSignalCustomerBlockingQueue<Integer> blockingQueue = new MoreProductSignalCustomerBlockingQueue(10);
		for (int i = 0; i < 1000; i ++) {
			int finalI = i;
			new Thread(()-> {
				try {
					blockingQueue.put(finalI);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}


		for (int i = 0; i < 1000; i ++) {
			new Thread(()-> {
				try {
					System.out.println(blockingQueue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
