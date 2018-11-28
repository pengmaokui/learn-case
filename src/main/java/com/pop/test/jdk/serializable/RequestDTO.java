package com.pop.test.jdk.serializable;

import java.io.Serializable;

/**
 * Created by pengmaokui on 2017/11/22.
 */
public class RequestDTO extends RequestParent implements Serializable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
