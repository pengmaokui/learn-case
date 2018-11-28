package com.pop.test.framework.spring.beans.depend.circular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pengmaokui on 2017/11/16.
 */
@Service
public class CircularA {
	@Autowired
	private CircularB circularB;

}
