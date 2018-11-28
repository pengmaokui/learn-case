package com.pop.test.jdk.concurrent.lock;

/**
 * Created by pengmaokui on 2017/11/24.
 */
public class Reader implements Runnable {

	private BufferInterruptibly buff;

	public Reader(BufferInterruptibly buff) {
		this.buff = buff;
	}

	@Override
	public synchronized void run() {
		try {
			buff.read();//可以收到中断的异常，从而有效退出
		} catch (InterruptedException e) {
			System.out.println("我不读了");
		}
		System.out.println("读结束");
	}
}
