package com.briup.io.ch12;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedStreamTest2 {
	public static void main(String[] args) throws IOException{
		PipedOutputStream p1 = new PipedOutputStream();
		PipedInputStream p2 = new PipedInputStream(p1);
		R r = new R(p1);
		R1 r1 = new R1(p2);
		r.start();
		r1.start();
	}
}

class R extends Thread{
	PipedOutputStream pos;
	private int i;
	public R(PipedOutputStream pos){
		this.pos =pos;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	//–¥»Î
	public void run(){
		for(i=0;i<=10;i++){
			try {
				sleep(500);
				pos.write(i);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
		try {
			pos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
//∂¡»°
class R1 extends Thread{
	PipedInputStream pis;
	public R1(PipedInputStream pis) {
		this.pis =pis;
	}
	public void run(){
		while(true){
			try {
				System.out.println(pis.read());
			} catch (IOException e) {
				//e.printStackTrace();
				break;
			}
		}
	}
}