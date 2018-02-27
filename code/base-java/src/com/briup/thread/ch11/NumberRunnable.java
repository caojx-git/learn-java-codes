package com.briup.thread.ch11;

public class NumberRunnable implements Runnable{
	public void run(){
		for(int i=0;i<=100;i++){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"i="+i);
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		NumberRunnable n = new NumberRunnable();
		Thread t1 = new Thread(n,"ooooooo");
		Thread t2 = new Thread(n,"pppp");
		t1.start();//启动线程
		t2.start();
		t1.join();	//主线程调用t1对象的join方法，则调用者（主线程）须等待t1线程执行完毕
		t2.join();
		Thread.sleep(2000);	//静态方法，不属于某个对象，属于某个类该处睡眠的是main
		System.out.println(Thread.currentThread().getName());
		System.out.println(Thread.currentThread().getPriority());
	}
}
