package com.briup.network.ch5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;


public class TimeUdpServer {
	public static void main(String[] args){
		System.out.println("udp服务器启动！");
		DatagramSocket ds = null;
		DatagramPacket dp = null;
		try {
			ds = new DatagramSocket(8989);
			byte[] b = new byte[100];
			dp = new DatagramPacket(b,b.length);
			ds.receive(dp);
			//time
			String s = new String(b);
			InetAddress add = dp.getAddress();
			int port = dp.getPort();
			if(s.trim().equals("time")){
				String time =new Date().toString();
				byte[] b1 = time.getBytes();
				dp = new DatagramPacket(b1,b1.length,add,port);
				ds.send(dp);
			}else{
				String error = "暗号错误";
				byte[] b1 =error.getBytes(); 
				dp = new DatagramPacket(b1,b1.length,add,port);
				ds.send(dp);
				System.out.println("数据传输完毕！");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			ds.close();
		}
	}
}
