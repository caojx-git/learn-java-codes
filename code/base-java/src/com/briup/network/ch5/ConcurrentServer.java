package com.briup.network.ch5;
import java.io.*;
import java.net.*;
import java.util.*;
public class ConcurrentServer {
	public static void main(String[] args) {
		System.out.println("1.服务器已启动222");
		ServerSocket ss =null;
		Socket s = null;
		try {
			//1.创建ServerSocket对象
			ss = new ServerSocket(8989);	
			while(true){
				s =ss.accept();	
				Handler h = new Handler(s);
				h.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

class Handler extends Thread{
	private Socket s;
	public Handler(Socket s){
		this.s = s;
	}
	public void run(){
		DataOutputStream dos = null;
		DataInputStream dis = null;
		try{
			sleep(10000);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			if(dis.readUTF().equals("time")){
				dos.writeUTF(new Date().toString());
				System.out.println("send date");
			}else{
				dos.writeUTF("暗号错误");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dos.close();
				dis.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
