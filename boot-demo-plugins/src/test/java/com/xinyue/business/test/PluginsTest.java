package com.xinyue.business.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

import com.xinyue.business.service.IMyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PluginsTest {
	
	@Autowired
	private IMyService myService;
	
	@Test
	public void myTest(){
		myService.add(1000);
		System.out.println("helle word");
	}
}
