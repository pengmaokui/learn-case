package com.pop.test.framework.spring.spel;

/**
 * Created by pengmaokui on 2017/4/22.
 */
public class AttachClazz {
	@MySpel({"#user.name + #user.age + 'method'"})
	public void method(User user) {
		System.out.println(user.getName());
	}
}
