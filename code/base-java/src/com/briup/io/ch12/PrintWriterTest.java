package com.briup.io.ch12;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PrintWriterTest {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter pw = null;
		pw = new PrintWriter("src/print","GBK");
		pw.print("qwer");
		pw.print("½ÜÆÕ");
		pw.flush();
		
		pw.close();
	}
}
