package com.pop.test.jdk.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Test;

/**
 * Created by pengmaokui on 2018/5/7.
 */
public class ConcurrentLinkedQueueTest {
	@Test
	public void test() {
		ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
		queue.add(new Integer(1));
	}
}
