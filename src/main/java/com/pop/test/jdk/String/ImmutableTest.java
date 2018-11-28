package com.pop.test.jdk.String;

/**
 * Created by pengmaokui on 2017/12/12.
 */
public class ImmutableTest {

	public static void main(String[] args) {
		ObjectTest objectTest = new ObjectTest();
		System.out.println(objectTest.getTest());
	}

	static class ObjectTest extends Object {

		private String test;

		public String getTest() {
			return test;
		}

		public void setTest() {
			this.test = "test";
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

		@Override
		public String toString() {
			setTest();
			return test;
		}

		@Override
		protected void finalize() throws Throwable {
			super.finalize();
		}
	}
}
