package com.pop.test.jdk.reflect.bean;

/**
 * Created by pengmaokui on 2018/7/19.
 */
public class MyRuntimeException extends RuntimeException {
	public MyRuntimeException() {
	}

	public MyRuntimeException(String message) {
		super(message);
	}

	public MyRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public MyRuntimeException(Throwable cause) {
		super(cause);
	}

	public MyRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
