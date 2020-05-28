package com.xinyue.business.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBooleanDemo {
	
	public static void main(String[] args) {
		AtomicBoolean atomicBoolean=new  AtomicBoolean();
		System.out.println("获取当前值："+atomicBoolean.get());//获取当前值
		atomicBoolean=new  AtomicBoolean(true);
		System.out.println("获取当前值："+atomicBoolean.get());//获取当前值
		
		//如果当前值为 ==的预期值，则将该值原子设置为给定的更新值。
		System.out.println("如果当前值为 ==的预期值，则将该值原子设置为给定的更新值。"+atomicBoolean.compareAndSet(true, false));
		System.out.println("获取当前值："+atomicBoolean.get());//获取当前值
		System.out.println("将原子设置为给定值并返回上一个值。"+atomicBoolean.getAndSet(true)); 
		System.out.println("获取当前值："+atomicBoolean.get());//获取当前值
		atomicBoolean.set(true);//无条件地设置为给定的值。
		System.out.println("获取当前值："+atomicBoolean.get());//获取当前值
		atomicBoolean.lazySet(false);//当线程执行完最终设定为给定值
		System.out.println("获取当前值："+atomicBoolean.get());//获取当前值s
	}
	
	private static class job implements Runnable{
		private AtomicBoolean a;
		public job(AtomicBoolean a){
			this.a=a;
		}
		public void run() {
			a.set(true);
			a.lazySet(false);
			if(a.get()){
				System.out.println(Thread.currentThread().getName()+":我的当前值："+a.get());
			}
			System.out.println(Thread.currentThread().getName()+"我还没有被改变："+a.get());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
