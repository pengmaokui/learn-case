package com.pop.test.jdk.reflect.bean;

/**
 * Created by pengmaokui on 2017/2/20.
 */
public interface Say<T> {
	void say(T t);

	void sayException(T t);
}
