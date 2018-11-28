package com.pop.test.framework.spring.spel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pengmaokui on 2017/4/22.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MySpel {
	/**
	 * 使用spel表达式动态生成需要的字符串
	 * @return
	 */
	String[] value() default  "";
}
