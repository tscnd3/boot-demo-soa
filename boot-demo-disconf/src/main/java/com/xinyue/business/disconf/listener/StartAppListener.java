package com.xinyue.business.disconf.listener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.baidu.disconf.client.store.processor.model.DisconfValue;
import com.baidu.disconf.client.usertools.DisconfDataGetter;

@Component
public class StartAppListener implements ApplicationListener<ContextRefreshedEvent>{
		
	@Autowired
	private Environment env;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("哈哈我被执行了...");
		ExecutorService executor = Executors.newCachedThreadPool();
		 executor.execute(	new Runnable() {
				public void run() {
					while (true) {
			             try {
			                 Thread.sleep(5000);
			                 String value=DisconfDataGetter.getByFile("application.properties").get("abc").toString();
			                 System.out.println("abc="+value);
			             } catch (InterruptedException e) {
			             }
			         }
				}
			});
	}
	
}
