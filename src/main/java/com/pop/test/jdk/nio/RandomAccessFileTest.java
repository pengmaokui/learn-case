package com.pop.test.jdk.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Test;

/**
 * Created by pengmaokui on 2018/7/15.
 */
public class RandomAccessFileTest {
	/**
	 * file.length是字节单位 b
	 * FileInputStream 的available的单位为字节 b
	 * @throws IOException
	 */
	@Test
	public void lengthUnitTest() throws IOException {
		RandomAccessFile file = new RandomAccessFile("/Users/pengmaokui/temp.html", "r");
		System.out.println(file.length());
		File bFile = new File("/Users/pengmaokui/temp.html");
		System.out.println(new FileInputStream(bFile).available());
	}
}
