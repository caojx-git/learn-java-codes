package com.briup.io.ch12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BufferTest {

	public static void main(String[] args) throws IOException {
		BufferedReader br = null;
		BufferedWriter bw = null;
	//	bw = new BufferedWriter(new FileWriter("src/string"));
		//br = new BufferedReader(new FileReader("src/string"));
		
		bw = new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream("src/string"),"GBK"));
	
		
		br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("src/string"),"GBK"));
		bw.write(97);		//"换行\回车"
		bw.write("你好\n\r");
		bw.flush();
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		System.out.println(br.readLine());
		bw.close();
		br.close();
	}

}
