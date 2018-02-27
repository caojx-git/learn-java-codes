package com.briup.io.ch12;
import java.io.*;
public class FileOutPutTest {
	
	public static void main(String[] args) {
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream("src/com/briup/io/ch12/test");
	
			for(int i=0;i<10;i++){
				fos.write(97);	//按照只字节写入
			}
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
