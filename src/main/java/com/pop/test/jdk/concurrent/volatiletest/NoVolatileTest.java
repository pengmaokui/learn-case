package com.pop.test.jdk.concurrent.volatiletest;

/**
 * Created by pengmaokui on 2017/12/6.
 */
public class NoVolatileTest {
	public int inc = 0;

	public void increase() {
		inc++;
		inc++;
	}

	public static void main(String[] args) {
		final NoVolatileTest test = new NoVolatileTest();
		System.out.println(Thread.activeCount());
		for(int i=0;i<10;i++){
			new Thread(){
				public void run() {
					for(int j=0;j<1000;j++)
						test.increase();
				};
			}.start();
		}

		while(Thread.activeCount()>2) {  //保证前面的线程都执行完
			System.out.println("线程没执行完，" + Thread.activeCount());
			Thread.yield();
		}
		System.out.println(test.inc);
	}
}
