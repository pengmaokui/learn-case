package com.pop.test.jdk.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试lock加锁可以被中断
 * Created by pengmaokui on 2017/11/24.
 */
public class BufferInterruptibly {
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void write() {
		lock.lock();
		try {
			long startTime = System.currentTimeMillis();
			System.out.println("开始往这个buff写入数据…");
			for (;;)// 模拟要处理很长时间
			{
				if (System.currentTimeMillis()
						- startTime > Integer.MAX_VALUE)
				break;
			}
			System.out.println("终于写完了");
		} finally {
			lock.unlock();
		}
	}

	public void read() throws InterruptedException {
		lock.lockInterruptibly();// 注意这里，可以响应中断
		try {
			System.out.println("从这个buff读数据");
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		BufferInterruptibly buff = new BufferInterruptibly();
		Writer writer = new Writer(buff);
		Reader reader = new Reader(buff);
		Thread threadw = new Thread(writer);
		Thread threadr = new Thread(reader);
		threadw.start();
		threadr.start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				long start = System.currentTimeMillis();
				for (;;) {
					if (System.currentTimeMillis()
							- start > 5000) {
						System.out.println("不等了，尝试中断");
						threadr.interrupt();
						break;
					}
				}
			}
		}).start();
	}
}
