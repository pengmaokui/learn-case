package com.pop.test.jdk.nio

import spock.lang.Specification

import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

/**
 * Created by pengmaokui on 2017/4/12.
 */
class FilesTest extends Specification {

	Path path = Paths.get("/Users/pengmaokui/Documents");

	def 'FileVisitor'() {
		Files.walkFileTree(path, new FileVisitor<Path>() {
			@Override
			FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				println("正在访问" + dir + "目录");
				return FileVisitResult.SKIP_SIBLINGS;
			}

			@Override
			FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				println("正在访问" + file + "文件");
				return FileVisitResult.SKIP_SUBTREE
			}

			@Override
			FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				println("访问文件" + file + "失败");
				return FileVisitResult.TERMINATE;
			}

			@Override
			FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				return FileVisitResult.SKIP_SIBLINGS;
			}
		})
		expect:
		1 == 1;
	}
}
