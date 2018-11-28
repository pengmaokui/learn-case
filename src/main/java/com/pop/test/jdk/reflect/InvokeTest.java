package com.pop.test.jdk.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.core.BridgeMethodResolver;

import com.pop.test.jdk.reflect.bean.Say;
import com.pop.test.jdk.reflect.bean.SayHelloImpl;

/**
 * Created by pengmaokui on 2017/2/20.
 */
public class InvokeTest implements InvocationHandler {

	private Object obj;

	public Object bind(Class<?> clazz, Object obj) {
		this.obj = obj;
		//创建代理对象
		return Proxy.newProxyInstance(clazz.getClassLoader(),
				new Class[]{clazz}, this);

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("进入了反射");
		System.out.println("method is bridge:" + method.isBridge());
		Object returnObj = method.invoke(obj, args);
		return returnObj;
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	/**
	 * method.invoke 有异常会抛出InvokerInvocationException
	 * 代理调用会转成UndeclaredThrowableException.class
	 * 代理调用只会略过RuntimeException|Error|method throw Exception
	 *
	 * 反射方法编译后的代码
	 *   try {
			 super.h.invoke(this, m3, (Object[])null);
		 } catch (RuntimeException | method throw Exception | Error var2) {
			 throw var2;
		 } catch (Throwable var3) {
			 throw new UndeclaredThrowableException(var3);
		 }
	 * 由于method.invoke()只要出现异常就会抛出InvocationTargetException异常。
	 * 解决办法就是：
	 * try {
	 *     method.invoke(obj, args);
	 * } catch (InvocationTargetException e) {
	 *     throw e.getCause(); //抛出方法异常;
	 * }
	 */
	@Test
	public void undeclaredThrowableExceptionTest() {
		expectedEx.expect(UndeclaredThrowableException.class);

		Say say = new SayHelloImpl();
		InvokeTest invokeTest = new InvokeTest();
		Say sayProxy = (Say) invokeTest.bind(Say.class, say);
		sayProxy.sayException("hello");

	}

	/**
	 * 多层代理嵌套
	 * 一下面的异常输出
	 * java.lang.reflect.UndeclaredThrowableException
	 * Caused by: java.lang.reflect.InvocationTargetException
	 * Caused by: java.lang.reflect.UndeclaredThrowableException
	 * Caused by: java.lang.reflect.InvocationTargetException
	 * Caused by: java.lang.RuntimeException: 123
	 */
	@Test
	public void doubleProxy() {
		expectedEx.expect(UndeclaredThrowableException.class);

		Say say = new SayHelloImpl();
		InvokeTest invokeTest = new InvokeTest();
		Say sayProxy = (Say) invokeTest.bind(Say.class, say);
		InvokeTest doubleInvokeTest = new InvokeTest();
		Say sayDoubleProxy = (Say) doubleInvokeTest.bind(Say.class, sayProxy);

		sayDoubleProxy.sayException("hello");
	}

	/**
	 * 测试bridgeMethod方法
	 */
	@Test
	public void methodIsBridge() throws NoSuchMethodException {
		Say say = new SayHelloImpl();
		Method sayMethod = SayHelloImpl.class.getMethod("say", Object.class);
		System.out.println("sayMethod.isBridge:" + sayMethod.isBridge());
	}

	/**
	 * 测试反射调用void方法返回值对象是啥
	 */
	@Test
	public void voidMethodReturnObject() {
		Say say = new SayHelloImpl();
		InvokeTest invokeTest = new InvokeTest();
		Say sayProxy = (Say) invokeTest.bind(Say.class, say);
		sayProxy.say("hello");
	}

	@Test
	public void test() throws NoSuchMethodException {
		Method sayMethod = SayHelloImpl.class.getMethod("say", Object.class);

		BridgeMethodResolver.findBridgedMethod(sayMethod);
	}

}
