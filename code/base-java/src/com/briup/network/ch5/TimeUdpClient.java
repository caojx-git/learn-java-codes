package com.briup.network.ch5;

import java.util.*;
import java.io.IOException;
import java.net.*;

public class TimeUdpClient {
	public static void main(String[] args) {
		DatagramSocket socket = null;
		DatagramPacket packet = null;
		//jvm 会自动分配端口号
		try {
			socket = new DatagramSocket();
			String s = "time";
			byte[] b = s.getBytes();
			packet = new DatagramPacket(b,b.length,InetAddress.getLocalHost(),8989);
			System.out.println(InetAddress.getLocalHost());
			socket.send(packet);
			byte[] b1 = new byte[100];
			packet = new DatagramPacket(b1,b1.length);
			socket.receive(packet);
			String time = new String(b1);
			System.out.println(time);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			socket.close();
		}
		

	}

}
