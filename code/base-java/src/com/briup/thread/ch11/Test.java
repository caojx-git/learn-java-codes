package com.briup.thread.ch11;

public  class Test {
	public static void main(String[] args){
		Handler h = new Handler();
		Producer p = new Producer(h);
		Consumer c = new Consumer(h);
		c.start();
		p.start();
	}
}

class Product{
	private int i;
	public int getI(){
		return i;
	}
	public void setI(int i){
		this.i=i;
	}
}

class Handler{
	private Product[] ps;
	private int count;
	public Handler() {
		ps = new Product[10];
	}
	public synchronized Product pop(){
		if(count==0){
			try{
				this.wait();	//µÈ´ý
			}catch(InterruptedException e){
				
			}
			//return null;
		}
		//return ps[--count];
		Product p =ps[--count];
		System.out.println("È¡-->"+p);
		return p;
	}
	
	public synchronized void push(Product p){
		if(count==ps.length){
			return;
		}
		ps[count++] =p;
		System.out.println("push end"+"-->"+count);
		this.notifyAll();	//»½ÐÑµÈ´ý
	}
	public int getCount(){
		return count;
	}
}

class Producer extends Thread{
	private Handler h;

	public Producer(Handler h) {
		super();
		this.h = h;
	}
	public void run(){
		//while(true){
		for(int i=0;i<10;i++){
			h.push(new Product());
			System.out.println("push end-->"+h.getCount());
			
			/*try {
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		//}
		}
	}
}
class Consumer extends Thread{
	private Handler h;
	public Consumer() {
	}
	public Consumer(Handler h) {
		this.h = h;
	}
	public void run(){
		while(true){
			//System.out.println(h.pop());
			h.pop();
		}
	}
	
}