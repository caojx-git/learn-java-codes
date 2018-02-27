package com.briup.thread.ch11;

public class InterruptedTest extends Thread{
	public void run(){
		for(int i=1;i<20;i++){
			System.out.println("i="+i);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		InterruptedTest t = new InterruptedTest();
		t.start();
		t.interrupt();
		System.out.println(t.isInterrupted());
		Thread.sleep(100);
		//t.interrupt();
		System.out.println(t.isInterrupted());	//isInterrupted可能会回到重新运行状态
		System.out.println(interrupted());  //interrupted是静态的
	
	}
}
