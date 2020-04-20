package com.xinyue.business.plugins.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
	
	private Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
	
	@Pointcut("execution(public * com.xinyue.business.service.impl..*.*(..))")
    public void Pointcut() {}
	
	@Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
		LOGGER.info("around执行方法之前");
        Object object = pjp.proceed();
        LOGGER.info("around执行方法之后--返回值：" +object);
        return object;
    }


}
