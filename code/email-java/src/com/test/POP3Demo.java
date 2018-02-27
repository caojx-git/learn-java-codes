package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.NonWritableChannelException;

public class POP3Demo {
	private static String POP3Server = "pop3.yeah.net";
	private static String USERNAME ="CJXiangemail@yeah.net";
	private static String PASSWORD = "Wysmtp123";
	public static void main(String[] args) {
		int POP3Port = 110;
		Socket client = null;
		InputStream in = null;
		
		try {
			//建立连接
			client = new Socket(POP3Server, POP3Port);
			//创建BufferedRead对象，以便从套接字中读取输出
			in = client.getInputStream();
			System.out.println("建立连接成功");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			//创建一个PrintWriter对象，以便向套接字写入内容
			OutputStream out  = client.getOutputStream();
			PrintWriter pWriter = new PrintWriter(out, true);
			//显示SMTP服务程序的握手过程
			System.out.println("s:"+br.readLine());
			pWriter.println("user "+USERNAME);
			System.out.println("s:"+br.readLine());
			pWriter.println("pass "+PASSWORD);
			System.out.println("s:"+br.readLine());
			pWriter.println("stat");
			
			System.out.println("获取邮件数");
			String[] temp = br.readLine().split(" ");
			
			for(int i = 0;i<temp.length;i++){
				System.out.println("temp:"+temp[i]);
			}
			//获取信箱中共有多少邮件
			int count =  Integer.parseInt(temp[1]);
			System.out.println("一共有"+count+"封邮件");
			//依次打印邮件的内容
			OutputStream outputStream  = null;
			for(int i = 1;i<count+1;i++){
				pWriter.println("retr "+i);
				System.out.println("以下为第"+i+"封邮件的内容");
				File file = new File("E:\\mail\\emial"+i+".eml");
				outputStream = new FileOutputStream(file, true);
				PrintWriter pw = new PrintWriter(outputStream);
				while(true){
					String reply =  br.readLine();
					System.out.println(reply);
					pw.write(reply+"\n");
					if (reply.toLowerCase().equals(".")) {
						break;
					}
				}
				pw.flush();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
		}
	}
}
