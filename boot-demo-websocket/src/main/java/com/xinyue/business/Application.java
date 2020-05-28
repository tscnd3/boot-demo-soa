package com.xinyue.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Robert HG (254963746@qq.com) on 4/9/16.
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.xinyue.business","com.xinyue.framework.disconf","com.xinyue.framework.redis"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
        
}
