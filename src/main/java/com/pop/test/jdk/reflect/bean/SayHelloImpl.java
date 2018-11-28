package com.pop.test.jdk.reflect.bean;

/**
 * Created by pengmaokui on 2017/2/20.
 */
public class SayHelloImpl implements Say<String> {

	@Override
	public void say(String str) {
		System.out.println(str);
	}

	public void say(SayHelloImpl sayHello) {
		System.out.println("sayHello");
	}

	@Override
	public void sayException(String s) {
		System.out.println(s);
		throw new RuntimeException("123");
	}

	private void sayPrivate() {
		System.out.println("PrivateHello");
	}
}
