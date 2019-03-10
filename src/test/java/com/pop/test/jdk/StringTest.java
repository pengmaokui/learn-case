package com.pop.test.jdk;


import org.junit.Test;

/**
 * @author：maokuie.peng
 * @since：22:25
 * @version:
 */
public class StringTest {

	@Test
	public void testIntern() {
		String str = "计算机软件";
		System.out.println(str.intern() == str);
	}
}
