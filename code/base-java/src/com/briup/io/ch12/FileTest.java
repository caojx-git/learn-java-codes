package com.briup.io.ch12;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileTest {
	public FileTest() {
	}
	public static void main(String[] args) throws IOException{
	
		FileWriter fw = null;
		FileReader fr = null;
		fw = new FileWriter("src/string");
		fr = new FileReader("src/string");
		
		
		fw.write("你好");
		fw.flush();
		char[] c = new char[10];
		//read 方法读取的字符数
		System.out.println(fr.read(c));                                                                                                 
		//输出字符数组中的字符
		System.out.println(c);
		
	}
}
