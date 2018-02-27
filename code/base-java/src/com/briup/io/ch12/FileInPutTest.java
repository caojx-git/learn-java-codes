package com.briup.io.ch12;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInPutTest {
	public static void main(String[] args){
		FileInputStream fis = null;
		//1.声明流
		try {
			fis = new FileInputStream("src/com/briup/io/ch12/test");
			int count=fis.available();
			byte[] b = new byte[count];
			//从0开始，跳过两个字节
			//fis.skip(2);
			for(int i=0;i<count;i++){
				int r = fis.read();		//read每次读取一个字节，返回整形
				System.out.println(r);
				b[i] =(byte)r;
			}
			//fis.read(b);		//读取指定数组长度的字节数，并将数据存放在数组中
			String s = new String(b);
			//System.out.println(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();	//关闭流
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
