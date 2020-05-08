package com.xinyue.business.lts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinyue.framework.lts.LtsClient;



/**
 * @author Robert HG (254963746@qq.com) on 4/9/16.
 */
@Component
public class JobClientReferenceBean implements InitializingBean {

    /**
     * 自己的业务类,就可以这样引用了
     */
    @Autowired
    private LtsClient ltsClient;

    @Override
    public void afterPropertiesSet() throws Exception {
    //	cronJob();
   // 	realtimeJob();
    	repeatJob();
    //	triggerTimeJob();
    }
    
    public void cronJob(){
    	Map<String,String> params=new HashMap<>();
    	params.put("name", "cronJob==========");
    	ltsClient.submitCronJob("com.xinyue.business.LstRunClass", "cronJobTest", params, "0 0/5 * * * ?", "200", 0);
    }
    
    public void realtimeJob(){
    	Map<String,String> params=new HashMap<>();
    	params.put("name", "realtimeJob===========");
    	ltsClient.submitRealtimeJob("com.xinyue.business.LstRunClass", "realtimeJobTest", params, 0);
    }
 
    public void repeatJob(){
    	Map<String,String> params=new HashMap<>();
    	params.put("name", "repeatJob===========");
    	ltsClient.submitRepeatJob("com.xinyue.business.LstRunClass", "repeatJobTest", params, 1, 300000, 0);
    }
    
    public void triggerTimeJob(){
    	Map<String,String> params=new HashMap<>();
    	params.put("name", "triggerTimeJob===========");
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar c = new GregorianCalendar();
    	Date triggerTime = new Date();
    	System.out.println("系统当前时间      ："+df.format(triggerTime));
    	c.setTime(triggerTime);//设置参数时间
    	c.add(Calendar.SECOND,30);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
    	triggerTime=c.getTime(); 
    	String str = df.format(triggerTime);
    	System.out.println("系统前30秒时间："+str);
    	ltsClient.submitTriggerTimeJob("com.xinyue.business.LstRunClass", "triggerTimeJobTest", params, triggerTime, 0);
    }
}
