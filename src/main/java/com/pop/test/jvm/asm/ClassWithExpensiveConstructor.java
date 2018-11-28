package com.pop.test.jvm.asm;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;

import org.junit.Test;

import sun.reflect.ReflectionFactory;

public class ClassWithExpensiveConstructor {

  private final int value;

  private ClassWithExpensiveConstructor() {
	  value = doExpensiveLookup();
  }

  private int doExpensiveLookup() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return 1;
  }

  public int getValue() {
    return value;
  }

  /**
   * Object类空构造函数替换
   * @throws Exception
   */
  @Test
  public void testReflectionFactory() throws Exception {
    @SuppressWarnings("unchecked")
    Constructor<ClassWithExpensiveConstructor> silentConstructor = (Constructor<ClassWithExpensiveConstructor>)
            ReflectionFactory.getReflectionFactory()
                    .newConstructorForSerialization(ClassWithExpensiveConstructor.class, Object.class.getConstructor());
    silentConstructor.setAccessible(true);
    assertEquals(0, silentConstructor.newInstance().getValue());
  }

  /**
   * 替换构造函数赋值
   * @throws Exception
   */
  @Test
  public void testStrangeReflectionFactory() throws Exception {
    @SuppressWarnings("unchecked")
    Constructor<ClassWithExpensiveConstructor> silentConstructor = (Constructor<ClassWithExpensiveConstructor>)
            ReflectionFactory.getReflectionFactory()
            .newConstructorForSerialization(ClassWithExpensiveConstructor.class,
                    OtherClass.class.getDeclaredConstructor());
    silentConstructor.setAccessible(true);
    ClassWithExpensiveConstructor instance = silentConstructor.newInstance();
    assertEquals(10, instance.getValue());
    assertEquals(ClassWithExpensiveConstructor.class, instance.getClass());
    assertEquals(Object.class, instance.getClass().getSuperclass());
  }
}