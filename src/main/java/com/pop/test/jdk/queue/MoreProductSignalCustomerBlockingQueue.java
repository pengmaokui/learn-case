package com.pop.test.jdk.queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多生产者单消费者
 * Created by pengmaokui on 2018/3/7.
 */
public class MoreProductSignalCustomerBlockingQueue<T> implements IBlockingQueue<T> {

	private Object[] item;

	private ReentrantLock putLock = new ReentrantLock();
	private ReentrantLock takeLock = new ReentrantLock();

	private Condition notFull = putLock.newCondition();
	private Condition notEmpty = takeLock.newCondition();

	private AtomicInteger putIndex = new AtomicInteger(0);
	private AtomicInteger takeIndex = new AtomicInteger(0);

	private int capacity;

	private AtomicInteger count = new AtomicInteger(0);

	public MoreProductSignalCustomerBlockingQueue(int capacity) {
		item = new Object[capacity];
		this.capacity = capacity;
	}

	@Override
	public void put(T t) throws InterruptedException {
		checkNotNull(t);
		//1.竞争index
		int c;
		for (;;) {
			int index = putIndex.get();
			//获取数组已被使用的个数
			c = count.get();
			if (c == capacity) {
				//等于最大长度 线程等待
				awaitNotFull();
			} else {
				//CAS竞争index
				if (putIndex.compareAndSet(index, index + 1)) {
					System.out.println("put index:" + index);
					item[index % capacity] = t;
					c = count.getAndIncrement();
					break;
				}
			}
		}

		//c+1 等于当前队列的个数
		if (c + 1 < capacity) {
			//有空位 唤醒等待线程
			signalNotFull(true);
		}

		if (c == 0) {
			//可用个数为零 获取消费者等待线程
			signalNotEmpty();
		}
	}

	@Override
	public T take() throws InterruptedException {
		//获取index
		int c;
		T data;
		for (;;) {
			int index = takeIndex.get();
			c = count.get();
			if (c == 0) {
				awaitNotEmpty();
			} else {
				if (takeIndex.compareAndSet(index, index + 1)) {
					System.out.println("take index:" + index);

					//获取index
					data = (T) item[index % capacity];
					c = count.getAndDecrement();
					break;
				}
			}
		}

		//c - 1 等于当前队列个数

		if (c > 1) {
			signalNotEmpty();
		}

		if (c == capacity) {
			signalNotFull(false);
		}
		return data;
	}

	private void checkNotNull(T t) {
//		if (t == null) {
//			throw new NullPointerException("t not null");
//		}
	}

	public Condition getNotFull() {
		return notFull;
	}

	public Condition getNotEmpty() {
		return notEmpty;
	}

	private void awaitNotFull() throws InterruptedException {
		putLock.lockInterruptibly();
		try {
			notFull.await();
		} finally {
			putLock.unlock();
		}
	}

	private void signalNotFull(boolean self) throws InterruptedException {
		putLock.lockInterruptibly();
		try {
			notFull.signal();
		} finally {
			putLock.unlock();
		}
	}

	private void awaitNotEmpty() throws InterruptedException {
		takeLock.lockInterruptibly();
		try {
			notEmpty.await();
		} finally {
			takeLock.unlock();
		}
	}

	private void signalNotEmpty() throws InterruptedException {
		takeLock.lockInterruptibly();
		try {
			notEmpty.signal();
		} finally {
			takeLock.unlock();
		}
	}

	private int getPutIndex() {
		int index;
		for (;;) {
			index = putIndex.get();
			if (index + 1 == capacity) {
				if (putIndex.compareAndSet(index, 0)) {
					break;
				}
			} else {
				if (putIndex.compareAndSet(index, index + 1)) {
					break;
				}
			}
		}
		return index;
	}

	private int getTakeIndex() {
		int index;
		for (;;) {
			index = takeIndex.get();
			if (index + 1 == capacity) {
				if (takeIndex.compareAndSet(index, 0)) {
					break;
				}
			} else {
				if (takeIndex.compareAndSet(index, index + 1)) {
					break;
				}
			}
		}
		return index;
	}
}
