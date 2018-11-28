package com.pop.test.framework.spring.intercepter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by pengmaokui on 2018/5/24.
 */
public class TestIntercepter implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		return methodInvocation.proceed();
	}
}
