package com.xinyue.business.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
	 //公平锁保证按队列中顺序获取锁，但是谁不保证先进队列， 能保证所有队列的线程都可以顺序执行
	 private static Lock fairLock = new ReentrantLockMine(true);
	 
	 //公平锁保证按队列中顺序执行，队列中 随机获取锁
	 private static Lock unfairLock = new ReentrantLockMine(false);
	 
	 public static void main(String[] args) throws Throwable {
		 ReentrantLockDemo demo=new ReentrantLockDemo();
		 demo.testLock("非公平锁", unfairLock);
		// demo.testLock("公平锁", fairLock);
	}
	 
	 private void testLock(final String lockName,Lock lock){
		 for (int i = 0; i < 10; i++) {
			 Thread t= new Thread(new job(lock)){
				 public String toString() {//重写toString
					return lockName+getName();
				 }
			 };
			 t.setName(""+i);
			 t.start();
		}
	 }
	 
	//重新实现ReentrantLock类是为了重写getQueuedThreads方法，便于我们试验的观察
	 private static class ReentrantLockMine extends ReentrantLock {  
		          public ReentrantLockMine(boolean fair) {
		              super(fair);
		          }
		  
		          @Override
		          protected Collection<Thread> getQueuedThreads() {   //获取同步队列中的线程
		              List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
		              Collections.reverse(arrayList);
		              return arrayList;
		          }
	}
	 
	 //业务
	private class job implements Runnable{
		private Lock lock;
		public job(Lock lock){
			this.lock=lock;
		}
		public void run() {
			lock.lock();
			try {
			//	Thread.sleep(200);//业务耗时
				System.out.println("获取锁的当前线程[" + Thread.currentThread().getName()+"], 同步队列中的线程" + ((ReentrantLockMine)lock).getQueuedThreads() + "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			lock.unlock();
		}
	}
	
}
