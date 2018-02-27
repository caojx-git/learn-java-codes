package com.briup.io.ch12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataStreamTest {

	public static void main(String[] args) throws IOException {
		 String name=null;
		 assert (name!=null):"变量name为空null";
		DataOutputStream dos = null;
		DataInputStream din = null;
		dos = new DataOutputStream(new FileOutputStream("src/data"));
		din = new DataInputStream(new FileInputStream("src/data"));
		
		/*dos.writeUTF("何伟");
		dos.writeUTF("湘南");
		dos.writeUTF("wqwqwqasas");*/
		dos.writeChars("wqwqwqasas");
		dos.flush();
		/*System.out.println(din.readUTF());*/
		System.out.println(din.readChar());
		dos.close();
		din.close();
	}

}
