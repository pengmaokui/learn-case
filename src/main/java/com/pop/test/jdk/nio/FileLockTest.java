package com.pop.test.jdk.nio;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * Created by pengmaokui on 2017/7/4.
 */
public class FileLockTest {
	@Test
	public void FileLock() throws IOException {
		Path path = Paths.get("/Users/pengmaokui/tbl_customer.sql");
		FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE);
		FileLock fileLock = channel.lock();

		System.out.println(fileLock.isShared());

		try (FileLock fileLock1 = channel.lock()) {
			System.out.println(fileLock1.isShared());
		}
	}
}
