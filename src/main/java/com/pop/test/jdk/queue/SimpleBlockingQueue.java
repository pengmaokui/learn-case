package com.pop.test.jdk.queue;

import java.util.LinkedList;

/**
 * Created by pengmaokui on 2018/3/7.
 */
public class SimpleBlockingQueue<T> implements IBlockingQueue<T> {

	private LinkedList<T> item = new LinkedList<>();

	private Object lock = new Object();

	private int count;


	public SimpleBlockingQueue(){
		this(10);
	}
	public SimpleBlockingQueue(int queueSize) {
		if(queueSize<1){
			throw new IllegalArgumentException("queueSize must be positive number");
		}
		this.count = queueSize;
	}

	@Override
	public void put(T o) throws InterruptedException {
		synchronized (lock) {
			if (item.size() >= this.count) {
				lock.wait();
			}
			item.addLast(o);
			lock.notifyAll();
		}
	}

	@Override
	public T take() throws InterruptedException {
		synchronized (lock) {
			if (item.size() <= 0) {
				lock.wait();
			}
			T data = item.removeFirst();
			lock.notifyAll();
			return data;
		}
	}
}
