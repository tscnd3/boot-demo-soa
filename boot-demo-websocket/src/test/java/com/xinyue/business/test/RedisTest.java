package com.xinyue.business.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.xinyue.framework.redis.util.RedisUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages={"com.xinyue.business","com.xinyue.framework.disconf","com.xinyue.framework.redis"})
public class RedisTest {
	
	@Autowired
	private RedisUtil redisUtil;
	
	 @Autowired
	 private StringRedisTemplate redisTemplate;
	
	
	@Test
	public void ssTest(){
		System.out.println("**********");
		redisUtil.set("reStringTest", 1,60*5);
	}
	
	@Test
	public void publishTest() throws InterruptedException{
		redisTemplate.convertAndSend("topic-1", "111111111");
		
		while(true){
			Thread.sleep(1000*5);
			//redisTemplate.get
		}
		
	}
	
	
}
