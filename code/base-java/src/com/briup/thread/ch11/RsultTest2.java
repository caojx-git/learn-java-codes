package com.briup.thread.ch11;

public class RsultTest2 {
	public static void main(String[] args){
		Result1 r = new Result1();
		Save1 s = new Save1(r);
		Fetch1 f = new Fetch1(r);
		s.start();
		f.start();
	}
}

class Fetch1 extends Thread{
	private Result1 r;	
	public Fetch1(){}
	public Fetch1(Result1 r){
		this.r = r;
	}
	public void run(){
		while(true){
			if(r.getI()==100)break;
			r.add();
			//System.out.println(getName()+"i="+r.getI());
		}
	}
}

class Save1 extends Thread{
	private Result1 r;
	public Save1(){}
	public Save1(Result1 r){
		this.r=r;
	}
	public void run(){
		while(true){
			if(r.getI()==100)break;
			r.add();
			//System.out.println(getName()+"i="+r.getI());
		}
	}
}

class Result1{
	private int i;
	public synchronized void add(){
		i++;
		System.out.println(Thread.currentThread().getName()+"+++++++++++++i="+i);
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	public void setI(int i){
		this.i=i;
	}
	public synchronized int getI(){
		return i;
	}
}