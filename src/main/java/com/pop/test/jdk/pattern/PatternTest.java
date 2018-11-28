package com.pop.test.jdk.pattern;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by pengmaokui on 2018/1/18.
 */
public class PatternTest {

	private Gson gson = new GsonBuilder().create();

	@Test
	public void sTest() {
		Pattern nameSeparator = Pattern.compile("\\s*[,]+\\s*");
		String value = "[]1 ,2 ,3,4,5";
		System.out.println(gson.toJson(nameSeparator.split(value)));
	}

	public void tokenTest() {
		StringTokenizer st = new StringTokenizer(",");
		String value = "[]1 ,2 ,3,4,5";
		System.out.println(st.countTokens());
	}
}
