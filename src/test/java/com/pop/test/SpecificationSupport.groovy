package com.pop.test

import com.pop.test.framework.spring.boot.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by pengmaokui on 2017/5/11.
 */
@SpringBootTest(classes= Application, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration("/application-context.xml")
class SpecificationSupport extends Specification {
	@Shared
	MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	def setup() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	def uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
