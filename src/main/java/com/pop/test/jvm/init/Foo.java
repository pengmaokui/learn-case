package com.pop.test.jvm.init;

class Foo {
		int i = 1;

		Foo() {
			System.out.println(i);
			int x = getValue();
			System.out.println(x);
		}

		{
			i = 2;
		}

		protected int getValue() {
			return i;
		}
	}