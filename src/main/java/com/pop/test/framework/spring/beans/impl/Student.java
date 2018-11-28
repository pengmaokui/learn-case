package com.pop.test.framework.spring.beans.impl;

import org.springframework.stereotype.Service;

import com.pop.test.framework.spring.beans.People;

/**
 * Created by pengmaokui on 2018/5/24.
 */
@Service
public class Student implements People {
	@Override
	public void say() {
		System.out.println("i am student");
	}
}
