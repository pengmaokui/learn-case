package com.pop.test.jdk.queue;

/**
 * Created by pengmaokui on 2018/3/7.
 */
public interface IBlockingQueue<T> {
	void put(T t) throws InterruptedException;

	T take() throws InterruptedException;
}
