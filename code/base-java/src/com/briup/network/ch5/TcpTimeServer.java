package com.briup.network.ch5;

import java.io.*;
import java.net.*;
import java.util.Date;

public class TcpTimeServer {
	public static void main(String[] args){
		System.out.println("1.服务器已启动");
		ServerSocket ss = null;
		Socket s = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		try {
			//1.创建ServerSocket对象
			ss = new ServerSocket(8989);
			//2.建立连接并创建新的socket
			s = ss.accept();
			//3.建立io流
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			//4.接收数据
			String str = dis.readUTF();
			if(str.equals("time")){
				//5.发送数据
				dos.writeUTF(new Date().toString());
			}else{
				dos.writeUTF("暗号错误");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				dos.close();
				dis.close();
				s.close();
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
