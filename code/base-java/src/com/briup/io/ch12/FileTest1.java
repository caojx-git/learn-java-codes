package com.briup.io.ch12;

import java.io.File;
import java.io.IOException;

public class FileTest1 {
	public static void main(String[] args){
		
		String name2 = "张三";
		String name1 = "张三";
		String name3 = new String("张三");
		name3 = name3.intern();
		System.out.println(name2 == name1);
		System.out.println(name3 == name1);
		
		File file = new File("D:/workspace/xiangnanBriupJava/src/print");
		System.out.println(file.exists());
		File file1 =new File("./src/print");
		
		File file2 = new File("D:/workspace/xiangnanBriupJava/src","print");
		
		File file3 = new File(new File("src"),"print");
		System.out.println(file3.exists());
		System.out.println(file3.getAbsolutePath());
		System.out.println(file3.isDirectory());
		System.out.println(file3.isFile());
		
		File file4 = new File("D:/");
		String[] files = file4.list();
		for(String name:files){
			System.out.println(name);
		}
		String s =System.getProperty("user.dir");
		System.out.println(s+"-----");
		
		File f1 = new File("d:/a");
		try {
			f1.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
