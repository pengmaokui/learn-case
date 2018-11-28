package com.pop.test.jdk.concurrent;

import java.util.HashSet;
import java.util.Set;

/**
 * i++汇编发生的逻辑i = Integer.valueOf(i.intValue() + 1);
 * 由于Integer.valueOf()返回的新的对象,所以synchronized相当于对两个对象加锁,不能保证线程安全
 * 引发问题:synchronized等待时加锁对象会不会变
 */
public class IPlusPlusTest implements Runnable{

	private static Integer i = new Integer(0);

	private static int count = 1000;

	private static Set<Integer> set = new HashSet<>();

	@Override
	public void run() {
		while(true) {
			synchronized (i) {
				if (i < count) {
					i++;
					set.add(i);
					System.out.println("i = " + i);
				} else {
					break;
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		IPlusPlusTest test1 = new IPlusPlusTest();
		IPlusPlusTest test2 = new IPlusPlusTest();
		new Thread(test1).start();
		new Thread(test2).start();
		i++;

		while(true) {
			Thread.sleep(500);
			if (Thread.activeCount() == 1) {
				System.out.println(count == set.size());
				break;
			}
		}
	}
}
