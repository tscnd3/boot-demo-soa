package com.xinyue.business.lts;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LstRunClass {

	public void cronJobTest(Map<String,String> map){
		System.out.println(map.get("name"));
	}
	
	public void realtimeJobTest(Map<String,String> map){
		System.out.println(map.get("name"));
	}
	
	public void repeatJobTest(Map<String,String> map){
		System.out.println(map.get("name"));
	}
	
	public void triggerTimeJobTest(Map<String,String> map){
		System.out.println(map.get("name"));
	}
	
}
