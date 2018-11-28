package com.pop.test.jdk.reflect;

import java.lang.reflect.Field;

/**
 * Created by pengmaokui on 2017/12/14.
 */
public class ReflectDTO {
	private String[][] files;

	private Integer[][] integers;

	public static void main(String[] args) {
		Field[] fieldList = ReflectDTO.class.getDeclaredFields();
		System.out.println(fieldList[0].getType().getName());
		System.out.println(fieldList[1].getType().getName());

	}
}
