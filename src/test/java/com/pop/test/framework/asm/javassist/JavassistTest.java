package com.pop.test.framework.asm.javassist;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class JavassistTest {
	/**
	 * 使用javassist生成java类，同时可以使用Class.forName找到该类
	 */
	@Test
	public void generatorClass() throws ClassNotFoundException {
		ClassGenerator classGenerator = ClassGenerator.newInstance();
		String className = "com.pop.test.framework.asm.javassist.ITest";
		classGenerator.setClassName(className);
		Class<?> clazz = classGenerator.toClass();
		Class<?> findClazz = Class.forName(className);
		Assert.assertEquals(className, clazz.getName());
		Assert.assertEquals(clazz, findClazz);
		URL url = Thread.currentThread().getContextClassLoader().getResource(className);
		Assert.assertNull(url);
	}

	/**
	 * 使用javassist生成java类，同一个Pool才能找到，新建的pool不能找到；
	 * 分析原因：
	 * javassist获取类通过classLoader.getResource(ClassName)来获取类，但是生成的类的ResourceURL为null。所以找不到该类
	 * 同一个pool能找到是因为找的时候会去缓存里面找，创建的时候会进行换缓存，所以不会报错
	 */
	@Test
	public void useGeneratorClass() {

		ClassGenerator classGenerator = ClassGenerator.newPool();
		String className = "com.pop.test.framework.asm.javassist.ITest";
		classGenerator.setClassName(className);
		classGenerator.toClass();

		ClassGenerator classGenerator1 = ClassGenerator.newPool();
		String className1 = "com.pop.test.framework.asm.javassist.TestImpl";
		classGenerator1.setClassName(className1);
		classGenerator1.setSuperClass(className);
		classGenerator1.toClass();
	}
}
