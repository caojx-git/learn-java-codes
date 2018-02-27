package com.briup.thread.ch11;

public class NumberThread extends Thread{
	public void run(){	//Runable
		for(int i=0;i<=100;i++){
			System.out.println(getName()+"i="+i);
		}
			
	}
	public static void main(String[] args){
		NumberThread n1 = new NumberThread();
		NumberThread n2 = new NumberThread();
		n1.start();
		n2.start();
	}
}
