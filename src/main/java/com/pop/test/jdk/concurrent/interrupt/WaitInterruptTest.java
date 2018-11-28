package com.pop.test.jdk.concurrent.interrupt;

/**
 * wait被打断测试
 * wait被中断也许获取线程锁才会throw InterruptedException
 *
 * 输出日志:
 * wait开始--1542278151415
 * 线程中断完成--1542278151601
 * 释放锁--1542278161603
 * java.lang.InterruptedException
 * 	at java.lang.Object.wait(Native Method)
 * 	at my.jdk.concurrent.interrupt.WaitInterruptTest$Task.run(WaitInterruptTest.java:34)
 * Created by pengmaokui on 2017/11/24.
 */
public class WaitInterruptTest {

	public static void main(String[] args) throws InterruptedException {
		Task task = new Task();
		task.start();

		new Thread(() -> {
			synchronized (task) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("释放锁--" + System.currentTimeMillis());
			}
		}).start();
		task.interrupt();
		System.out.println("线程中断完成--" + System.currentTimeMillis());
	}

	static class Task extends Thread{

		@Override
		public void run(){
			synchronized(this){
				try {
					System.out.println("wait开始--" + System.currentTimeMillis());
					wait(1000);
					System.out.println("wait结束--" + System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
