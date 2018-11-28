package com.pop.test.jvm.asm;

public class OtherClass {

	private final int value;
	private final int unknownValue;

	private OtherClass() {
		System.out.println("test");
		this.value = 10;
		this.unknownValue = 20;
	}
}