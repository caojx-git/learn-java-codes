package com.briup.network.ch5;

import java.io.*;
import java.net.*;

public class TimeTcpClient {

	public static void main(String[] args) {
		System.out.println("客户端启动！");
		Socket s = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		try {
			//1.建立用于接受和发送数据的Socket对象
			//Socket(String host,int port)
			s = new Socket("127.0.0.1",8989);
			//2.建立输入输出流用于接受和发送数据
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			//3.发送数据
			dos.writeUTF("time");
			//4.接受数据
			String str = dis.readUTF();
			System.out.println(str);
		} catch(IOException e) {
			e.printStackTrace();
		}finally{//释放资源
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
