package com.pop.test.framework.spring.beans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.pop.test.framework.spring.beans.impl.Student;


/**
 * Created by pengmaokui on 2018/5/24.
 */
@Service
public class GetBeanByImplTest implements ApplicationContextAware {
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Object o2 = applicationContext.getBean(People.class);
		Object o = applicationContext.getBean(Student.class);
		System.out.println(o != null);
	}
}
