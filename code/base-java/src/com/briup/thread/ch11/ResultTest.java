package com.briup.thread.ch11;
/*≤¢∑¢∑√Œ 
 * */
public class ResultTest extends Thread{
	public static void main(String[] args){
		Result r = new Result();
		Save s = new Save(r);
		Fetch f = new Fetch(r);
		s.start();
		f.start();
	}
}
class Fetch extends Thread{
	private Result r;
	public Fetch(){}
	public Fetch(Result r){
		this.r = r;
	}
	public void run(){
		while(true){
			synchronized(r){
				int i = r.getI();
				if(i==100){
					break;
				}
				i++;
				r.setI(i);
				System.out.println(getName()+"i="+r.getI());
			}
		}
	}
}

class Result{
	private int i;
	public Result(){}
	public Result(int i){
		this.i=i;
	}
	public int getI() {
		return i;
	}
	public  void setI(int i) {
		this.i = i;
	}
}

class Save extends Thread{
	private Result r;
	public Save(){}
	public Save(Result r){
		this.r=r;
	}
	public void run(){
		while(true){
			synchronized(r){
				int i = r.getI();
				if(i==100){
					break;
				}
				i++;
				r.setI(i);
				System.out.println(getName()+"i="+r.getI());
			}
		}
	}
}
