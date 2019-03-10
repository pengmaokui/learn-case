package com.pop.test.jdk.memoryleak;

import org.junit.Test;

import com.pop.test.jdk.cl.TestClassLoader;

import sun.misc.GC;

/**
 * @author：maokuie.peng
 * @since：16:22
 * @version:
 */
public class GCTest {
	/**
	 * 测试不同classLoader调用GC.requestLatency,
	 * 由于GC.requestLatency调用会产生守护线程.
	 * 检测:
	 * 	1.不同的classLoader调用会产生几次守护线程
	 * 	2.JVM初始化时有没有初始化守护线程
	 * 结果
	 *  1.守护线程是static资源,所以只会产生一个
	 *  2.JVM初始化时没有初始化守护线程
	 */
	@Test
	public void requestLatencyTest() {
		TestClassLoader cl1 = new TestClassLoader();
		TestClassLoader cl2 = new TestClassLoader();
		Thread.currentThread().setContextClassLoader(cl1);
		GC.requestLatency(Long.MAX_VALUE - 1L);

		Thread.currentThread().setContextClassLoader(cl2);
		GC.requestLatency(Long.MAX_VALUE - 2L);
	}
}
