package com.pop.test.framework.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.pop.test.framework.spring")
@ImportResource("classpath:application-context.xml")
public class Application {

	public static void main(String[] args) {
          SpringApplication.run(Application.class, args);
      }
}