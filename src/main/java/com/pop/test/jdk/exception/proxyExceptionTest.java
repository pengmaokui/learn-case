package com.pop.test.jdk.exception;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.pop.test.jdk.reflect.bean.Say;
import com.pop.test.jdk.reflect.bean.SayHelloImpl;


/**
 * 方法没有声明抛出异常也可以抛出非运行时异常
 * Created by pengmaokui on 2017/5/3.
 */
public class proxyExceptionTest implements InvocationHandler {
	private Object obj;

	public Object bind(Object obj) {
		this.obj = obj;
		//创建代理对象
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
				obj.getClass().getInterfaces(), this);

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("进入了反射");
		throw new Exception("抛出异常");
	}

	public static void main(String[] args) {
		Say say = new SayHelloImpl();
		proxyExceptionTest proxyExceptionTest = new proxyExceptionTest();
		Say sayProxy = (Say) proxyExceptionTest.bind(say);

		try {
			sayProxy.say("hello");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
