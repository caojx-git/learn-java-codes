package com.briup.tomcat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args){
		ServerSocket server =null;
		Socket s=null;
		try {
			server = new ServerSocket(8989);
			// 当浏览器向8989发送请求时，accept方法会接受请求
			while (true) {
				// 并建立连接 ，返回socket对象
				s = server.accept();
				Handler h = new Handler(s);
				h.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//处理ServerSocket 接受的Soket对象，再创建发送过来的请求
class Handler extends Thread{
	Socket s;
	BufferedReader br;
	PrintWriter pw;
	DataOutputStream dos;
	DataInputStream dis;
	FileInputStream fis;
	String method, path, protocol;
	public Handler(Socket s){
		this.s = s;
	}
	public void run(){
		System.out.println(Thread.currentThread().getName()+"=====");
		try {
			//获取requset,读取浏览器中输入需要请求的文件
			br = new BufferedReader(new InputStreamReader(
					(s.getInputStream())));
			pw = new PrintWriter(s.getOutputStream());
			dos = new DataOutputStream(s.getOutputStream());
			String str="";
			while((str=br.readLine())!=null){
				System.out.println(str);
			}
			System.out.println(str+"-----");
			String[] ss = str.split(" ");
			method = ss[0];
			path = ss[1];
			protocol = ss[2];
		/*	
			for(String s:ss){
				System.out.println(s+"---");
			}*/
			System.out.println(ss[1]+"------------------");
			//判断请求的文件是否存在
			File filePath = new File("webContent"+path);
			// 如果请求的文件不存在则反回404页面
			if (filePath.exists()) {
				// 响应头
				fis = new FileInputStream("webContent" + path);
				pw.println("HTTP/1.1 200 OK");
				pw.println("Server:McaServer");
				pw.println("Content-Type:text/html");
				pw.println("Content-Length:" + fis.available());
				pw.println();
				// 响应体
				byte[] buffer = new byte[fis.available()];
				int len = fis.read(buffer, 0, buffer.length);
				dos.write(buffer, 0, len);
				dos.flush();
			}else{
				// 响应头
				System.out.println("oooo------------------------------------");
				fis = new FileInputStream("webContent/notFound.html");
				pw.println("HTTP/1.1 404 Not Found");
				pw.println("Server:McaServer");
				pw.println("Content-Type:text/html");
				pw.println("Content-length:" + fis.available());
				pw.println();

				byte[] buffer = new byte[fis.available()];
				int len = fis.read(buffer, 0, buffer.length);
				dos.write(buffer, 0, len);
				dos.flush();
			}

		} catch(NullPointerException e){
			
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
	}

}
