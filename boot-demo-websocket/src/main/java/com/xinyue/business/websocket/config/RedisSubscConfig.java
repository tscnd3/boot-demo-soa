package com.xinyue.business.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import com.xinyue.business.websocket.listener.SubscribeListener;

@Configuration
public class RedisSubscConfig {
	
	 @Autowired
	 private JedisConnectionFactory jedisConnectionFactory;

	    /**
	     * @author 七脉 描述：需要手动定义RedisMessageListenerContainer加入IOC容器
	     * @return
	     */
	    @Bean
	    public RedisMessageListenerContainer redisMessageListenerContainer() {

	        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

	        container.setConnectionFactory(jedisConnectionFactory);

	        /**
	         * 添加订阅者监听类，数量不限.PatternTopic定义监听主题,这里监听dj主题
	         */
	        container.addMessageListener(new SubscribeListener(), new PatternTopic("topic-1"));
	        return container;

	   }
	
}
