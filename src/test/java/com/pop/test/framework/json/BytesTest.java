package com.pop.test.framework.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pop.test.framework.json.bean.TestObject;

public class BytesTest {
	@Test
	public void gson() throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get("/Users/pengmaokui/a.png"));
		TestObject testObject = new TestObject();
		testObject.setBytes(bytes);
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(testObject);
		System.out.println(json);
		testObject = gson.fromJson(json, TestObject.class);
		Files.write(Paths.get("/Users/pengmaokui/a.bak.png"), testObject.getBytes());
	}

	@Test
	public void fastjson() throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get("/Users/pengmaokui/a.png"));
		TestObject testObject = new TestObject();
		testObject.setBytes(bytes);
		String json = JSON.toJSONString(testObject);
		System.out.println(json);
		testObject = JSON.parseObject(json, TestObject.class);
		Files.write(Paths.get("/Users/pengmaokui/a.fjbak.png"), testObject.getBytes());
	}
}
