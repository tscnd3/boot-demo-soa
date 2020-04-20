package com.xinyue.business.plugins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(basePackages={"com.xinyue.business.*"})
//@ImportResource({"classpath:spring-disconf.xml"})//引入disconf
@EnableAspectJAutoProxy(exposeProxy=true)
public class Application {
	public static void main(String[] args) {
		System.out.println("111111111111111111111111111111111111111111111111111111111111111");
		SpringApplication.run(Application.class, args);
	}

}
