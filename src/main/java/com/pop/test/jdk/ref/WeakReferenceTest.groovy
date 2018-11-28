package com.pop.test.jdk.ref

import spock.lang.Specification

import java.lang.ref.ReferenceQueue
import java.lang.ref.WeakReference

/**
 * Created by pengmaokui on 2017/3/19.
 */
class WeakReferenceTest extends Specification {
	def 'test'() {
		when:
		Person person = new Person();
		person.setName("weak");
		WeakReference wf = new WeakReference(person);
		person = null;
		System.out.println("before gc... name:" + ((Person)wf.get()).getName());
		System.gc();
		Thread.sleep(1000);
		System.out.println("after gc... name:" + ((Person)wf.get()));
		then:
		1 == 1;
	}

	def 'testQueue'() {
		//when:

	}

	def 'testWeakHashMap'() {
		when:
		Map map = new WeakHashMap();
		ReferenceQueue queue = new ReferenceQueue();
		String str = new String("test");
		WeakReference wf = new WeakReference(str, queue);
		str = null;
		map.put(wf, "1");
		System.gc();
		Thread.sleep(1000);
		System.out.println(map.get(wf));

		then:
		map.size() == 0;

	}

	static class Person {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
