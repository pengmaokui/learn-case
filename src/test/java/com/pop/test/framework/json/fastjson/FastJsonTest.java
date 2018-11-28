package com.pop.test.framework.json.fastjson;


import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.pop.test.framework.json.fastjson.dto.NoGetMethodDTO;
import com.pop.test.framework.json.fastjson.dto.NoGetSetMethodDTO;

/**
 * Created by pengmaokui on 2018/7/23.
 */
public class FastJsonTest {
	/**
	 * 默认没有get方法不会序列化
	 */
	@Test
	public void noGetMethodSeriTest() {
		NoGetMethodDTO dto = new NoGetMethodDTO();
		dto.setName("test");

		Assert.assertEquals("{}", JSON.toJSONString(dto));
	}

	/**
	 * 添加serializeConfig 由于还没有看源码 猜测肯定是反射
	 */
	@Test
	public void notGetMethodSerializeTest() {
		NoGetMethodDTO dto = new NoGetMethodDTO();
		dto.setName("test");

		SerializeConfig serializeConfig = new SerializeConfig(true);
		Assert.assertEquals("{\"name\":\"test\"}", JSON.toJSONString(dto, serializeConfig));
	}

	@Test
	public void notSetMethodSeriTest() {
		NoGetSetMethodDTO dto = new NoGetSetMethodDTO();
		String json = "{\"name\":\"test\"}";
		dto = JSON.parseObject(json, NoGetSetMethodDTO.class);

		SerializeConfig serializeConfig = new SerializeConfig(true);
		ParserConfig parserConfig = new ParserConfig(true);
		Assert.assertEquals("{}", JSON.toJSONString(dto, serializeConfig));

		dto = JSON.parseObject(json, NoGetSetMethodDTO.class, parserConfig);
		Assert.assertEquals("{\"name\":\"test\"}", JSON.toJSONString(dto, serializeConfig));
	}
}
