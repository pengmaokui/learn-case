package com.pop.test.framework.spring.spel;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelTest {
	@Test
	public void spelTest() {
		InvokeClazz proxy = new InvokeClazz();
		AttachClazz clazz = (AttachClazz) proxy.getProxy(AttachClazz.class);
		clazz.method(new User("peng", 25));
	}

	@Test
	public void helloWorld() {
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression =
				parser.parseExpression("#result != null #a");
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("result", new Object());
		context.setVariable("a", new Object());
		Assert.assertEquals(true, expression.getValue(context));
	}

	class InvokeClazz implements MethodInterceptor {
		private Enhancer enhancer = new Enhancer();
		public Object getProxy(Class clazz){
			//设置需要创建子类的类
			enhancer.setSuperclass(clazz);
			enhancer.setCallback(this);
			//通过字节码技术动态创建子类实例
			return enhancer.create();
		}

		@Override
		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			ExpressionParser parser = new SpelExpressionParser();
			ParameterNameDiscoverer paramNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
			EvaluationContext context = new StandardEvaluationContext();
			//通过asm获取class的变量名
			String[] argNames = paramNameDiscoverer.getParameterNames(method);
			for (int i = 0, l = argNames.length; i < l; i++) {
				context.setVariable(argNames[i], objects[i]);
			}
			MySpel mySpel = method.getAnnotation(MySpel.class);
			MySpelContext mySpelContext = parseAnnotation(mySpel);
			//通过spel动态生成value值
			System.out.println(parser.parseExpression(mySpelContext.getValue()[0]).getValue(context, String.class) );
			return null;
		}

		private MySpelContext parseAnnotation(MySpel mySpel) {
			return new MySpelContext(mySpel.value());
		}
	}

	class MySpelContext {
		private String[] value;

		public MySpelContext(String[] value) {
			this.value = value;
		}

		public String[] getValue() {
			return value;
		}

		public void setValue(String[] value) {
			this.value = value;
		}
	}
}
