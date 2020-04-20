package com.xinyue.business.plugins.service.impl;

import org.springframework.stereotype.Service;

import com.xinyue.business.plugins.service.IMyService;

@Service("myService")
public class MyServiceImpl implements IMyService{

	@Override
	public void add(int a) {
		
		System.out.println("***********"+a);
	}

}
