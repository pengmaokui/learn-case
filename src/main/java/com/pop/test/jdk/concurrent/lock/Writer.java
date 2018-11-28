package com.pop.test.jdk.concurrent.lock;

/**
 * Created by pengmaokui on 2017/11/24.
 */
public class Writer implements Runnable {

	private BufferInterruptibly buff;

	public Writer(BufferInterruptibly buff) {
		this.buff = buff;
	}

	@Override
	public void run() {
		buff.write();
	}
}
