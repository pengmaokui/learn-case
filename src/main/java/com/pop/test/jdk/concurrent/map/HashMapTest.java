package com.pop.test.jdk.concurrent.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

/**
 * Created by pengmaokui on 2017/11/26.
 */
public class HashMapTest {

	@Test
	public void valueConcurrentTest() throws InterruptedException {
		Map<String, List<String>> map = new ConcurrentHashMap<>();
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		map.put("list", list);

		new Thread(() -> {
			List<String> mapList = map.get("list");
			for (int i = 0; i < 100; i++) {
				mapList.add(i + "");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(() -> {
			List<String> mapList = map.get("list");
			for (String item : mapList) {
				System.out.println(item);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		Thread.sleep(1000 * 60);
	}
}
