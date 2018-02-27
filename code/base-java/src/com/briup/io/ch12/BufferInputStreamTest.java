package com.briup.io.ch12;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BufferInputStreamTest {
	public static void main(String[] args) throws IOException{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream("src/buffer"));
			bis = new BufferedInputStream(new FileInputStream("src/buffer"));
			//bos.write();
			bos.write(97);
			bos.flush();
			
			System.out.println(bis.read());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			bos.close();
			bis.close();
		}
	}
}
