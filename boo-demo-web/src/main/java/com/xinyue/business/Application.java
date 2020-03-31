package com.xinyue.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.xinyue"})
//@ImportResource({"classpath:spring-disconf.xml"})//引入disconf
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
