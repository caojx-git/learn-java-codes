package com.briup.io.ch12;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		//1.封装文件对象
		File file = new File("src/com/briup/io/ch12/test");
		//2.建立读取文件的输入流
		FileInputStream fis = new FileInputStream(file);
		//3.建立具有过滤功能的输入流
		DataInputStream dis = new DataInputStream(fis);
		try {
			
			for(int i=0;i<10;i++){
				String str = dis.readLine();
				System.out.println(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				dis.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
