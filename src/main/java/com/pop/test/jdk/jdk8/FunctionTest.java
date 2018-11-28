package com.pop.test.jdk.jdk8;


import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by pengmaokui on 2017/4/15.
 */
public class FunctionTest {
	@Test
	public void SupplierTest() {
		Supplier stringSupplier = () -> "SupplierTest";
		Assert.assertEquals("SupplierTest", stringSupplier.get());
	}
}
