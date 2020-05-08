package com.xinyue.business.java;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {
	
	public static void main(String[] args) {
		//executeOne();
		LoopExecution();
	}
	
	
	/**
	 * 只执行一次
	 */
	public static void executeOne(){
		Timer timer = new Timer();

        //延迟1000ms执行程序
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("IMP 当前时间" + this.scheduledExecutionTime());
            }
        }, 1000);
        //延迟10000ms执行程序
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("IMP 当前时间" + this.scheduledExecutionTime());
            }
        }, new Date(System.currentTimeMillis() + 10000));
        
        System.out.println("===========11");
	}
	
	
	
	public static void LoopExecution(){
		Timer timer = new Timer();
		        //前一次执行程序结束后 2000ms 后开始执行下一次程序
		timer.schedule(new TimerTask() {
		            @Override
		            public void run() {
		                System.out.println("IMP 当前时间" + this.scheduledExecutionTime());
		            }
		}, 0,2000);
		
		        //前一次程序执行开始 后 2000ms后开始执行下一次程序
		timer.scheduleAtFixedRate(new TimerTask() {
		            @Override
		            public void run() {
		                System.out.println("IMP 当前时间" + this.scheduledExecutionTime());
		            }
		        },0,2000);
	}
	
	
}
