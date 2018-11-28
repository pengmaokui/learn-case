package com.pop.test.jvm;

/**
 * Created by pengmaokui on 2017/11/15.
 */
public class StaticDispatch {
	abstract class Human {

	}

	class Man extends Human{

	}

	class Wowan extends Human {

	}

	public void sayHello(Human gay) {
		System.out.println("hello gay");
	}

	public void sayHello(Man man) {
		System.out.println("hello man");
	}

	public void sayHello(Wowan wowan) {
		System.out.println("hello wowan");
	}

	public static void main(String[] args) {
		StaticDispatch sd = new StaticDispatch();
		Human man = sd.new Man();
		Human woman = sd.new Wowan();
		sd.sayHello(man);
		sd.sayHello(woman);
	}
}
